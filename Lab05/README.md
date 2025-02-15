# NMEC-114547

# LAB 5

## 5.1 Configure Apache Kafka with Docker 

### **Create topics**

```
docker exec lab05-kafka-1 kafka-topics --create --topic lab05 --partitions 1 --replication-factor 1 --bootstrap-server kafka:9092
```

This commands allows me to create topics. Topics organize messages in different categories. Note: they should have an unique name

### **Start consumer**

```
docker exec lab05-kafka-1 kafka-console-consumer --topic lab05 --from-beginning --bootstrap-server kafka:9092
```

Consumes messages from a certain topic. The notation (--from-beginning) is used to consume all messages sent over the topic from the start.

### **Start producer**

```
docker exec -it lab05-kafka-1 bash  
kafka-console-producer --topic lab05 --broker-list kafka:9092
```

Generates and sends messages into a certain topic.

### **Exercise f)**

When you open multiple consumer terminals, the messages sent to a topic will be distributed among all consumers within the same group, i.e. they will distribute the load of partitions read. Old messages will be consumed if the consumer has the --from-beginning flag.
When you open multiple producer terminals, all producers can still send messages to the same topic.


## 5.2 Create a producer and consumer

### Creating Poetry project

```
poetry add pendulum
poetry new poetry-demo
```

Inside poetry_demo folder, create the producer.py and the consumer.py scripts, that allows to add and consume messages into a topic

When having problems wit Python3 >= 3.12.X add these lines to the top of each script:

```
import sys
import sys, types

m = types.ModuleType('kafka.vendor.six.moves', 'Mock module')
setattr(m, 'range', range)
sys.modules['kafka.vendor.six.moves'] = m
```

### Last message

Received message: {"nMec": "114547", "generatedNumber": 75025, "type": "fibonacci"}

### Exercise d)

If the consumer is committing offsets and the messages are within the retention period, running the consumer multiple times will not read all the messages again, only the ones that are produced after the last offset it has committed.
If the consumer does not commit offsets the consumer will start from the beginning (in the case of no committed offset).

## 5.3 Create a consumer in Java integrated with Spring Boot  

Add two classes, consumer and producer that allow to send and receive messages from the python scripts previously created.

**Consumer**:
```
@Component
public class Consumer {
    @KafkaListener(topics = "lab05_114547", groupId = "myId")
    public void consume(Message message) {
        System.out.println("Received message: " + message);
    }
}
```

**Producer**:
```
@Component
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
```

application.properties file:

```
spring.application.name=kafka
spring.kafka.bootstrap-servers=localhost:29092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.consumer.properties.spring.json.value.default.type=com.app.kafka.Message
spring.kafka.producer.properties.spring.json.add.type.headers=false
```

Added these dependencies in the pom.xml:

```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
</dependency>
```

They provide the necessary libraries for Jackson, a JSON library used in Java for serializing and deserializing Java objects to and from JSON format.

## 5.4 Wrapping-up and integrating concepts 

To create the necessary topic for this exercise:

```
docker exec lab05-kafka-1 kafka-topics --create --topic lab05_quotes --partitions 1 --replication-factor 1 --bootstrap-server kafka:9092
```

### Exercise a)

Use the previous producer.py, with adaptions to produce movie quotes and adding them into a Kafka topic (lab05_quotes):

```
(...)
bootstrap_servers = 'localhost:29092'
topic_name = "lab05_quotes"

quotes = [
    {"movie": {"id": 1}, "quote": "I'm gonna make him an offer he can't refuse."},
    {"movie": {"id": 2}, "quote": "Life is like a box of chocolates, you never know what you're gonna get."},
    {"movie": {"id": 3}, "quote": "Why so serious?"},
    {"movie": {"id": 4}, "quote": "May the Force be with you."},
    {"movie": {"id": 5}, "quote": "I'm the king of the world!"},
]

producer = KafkaProducer(
    bootstrap_servers=bootstrap_servers,
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

print("Starting to generate quotes...")

try:
    while True:
        new_quote = random.choice(quotes)
        
        producer.send(topic_name, value=new_quote)
        print(f"Sent quote to Kafka: {new_quote}")

        time.sleep(random.randint(5, 10))

except KeyboardInterrupt:
    print("\nStopping quote generator...")
finally:
    producer.close()
```

### Exercise b)

Reusing the code from exercise 5.3 I added two classes: Message.java and QuoteConsumer.java.

The first helps me specify the format of the messages that are deserialized.
The second consumes the lab05_quotes topic, and receives the messages produced from the python script.

Finally I created another class KafkaConsumerConfig.java that is responsible for addressing how the application consumes messages. In this case, it consumes messages of type Quote, deserializes them from JSON to a Java object and makes them available for processing. It is thanks to this class that my consumer can consume directly a Quote and not a String:

```
@KafkaListener(topics = "lab05_quotes", groupId = "movie-app")
public void consume(Quote quote) {
    quoteRepository.save(quote);
    System.out.println("Received quote: " + quote);
}
```

KafkaConsumerConfig.java:
```
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Quote> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "movie-app");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<Quote> deserializer = new JsonDeserializer<>(Quote.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Quote> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Quote> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```


### Exercise c)

To extend the Frontend to show the latest 5 quotes, I added this endpoint into my controller:

```
@GetMapping("/quotes/latest")
public List<Quote> getLatestQuotes() {
    return quoteService.getLatestQuotes(5);
}
```

And this function on the QuoteService.java:

```
public List<Quote> getLatestQuotes(int count) {
    return repository.findAll()
            .stream()
            .sorted(Comparator.comparingLong(Quote::getId).reversed())
            .limit(count)
            .collect(Collectors.toList());
}
```

And fetched the latestQuotes through that endpoint in my App.js file:

```
const [latestQuotes, setLatestQuotes] = useState([]);
(...)
useEffect(() => {
const fetchLatestQuotes = async () => {
    try {
    const response = await client.get("/quotes/latest");
    setLatestQuotes(response.data);
    } catch (error) {
    console.error("Error fetching latest quotes:", error);
    }
};

fetchLatestQuotes();

const interval = setInterval(fetchLatestQuotes, 10000);

return () => clearInterval(interval); // Clean up interval on component unmount
}, []);
```

### Aditional notes
I know that it was suposed to use SpringBoot Websocket's to update quotes without refreshing the browser. Despite this, my application updates the latest quotes without having to refresh the browser. I know this was not the goal of the exercise, but due to a lack of time, I did not make any more changes to my code to complete exercise d).

Since I could not deploy the application to Docker containers, I used the docker-compose.yml file in the folder lab5_2 to start the kafka, and I ran everything else manually:

```
mvn spring-boot:run // MovieApp folder
npm start // my-app folder
python3 producer.py
```
