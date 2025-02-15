import sys
import sys, types
import random
import time

m = types.ModuleType('kafka.vendor.six.moves', 'Mock module')
setattr(m, 'range', range)
sys.modules['kafka.vendor.six.moves'] = m

from kafka import KafkaProducer
import json

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

