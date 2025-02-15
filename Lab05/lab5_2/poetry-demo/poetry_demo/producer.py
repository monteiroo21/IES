import sys
import sys, types

m = types.ModuleType('kafka.vendor.six.moves', 'Mock module')
setattr(m, 'range', range)
sys.modules['kafka.vendor.six.moves'] = m

from kafka import KafkaProducer
import json

def Fibonacci(n):
    if n == 0:
        return 0
    elif n == 1 or n == 2:
        return 1
    else:
        return Fibonacci(n-1) + Fibonacci(n-2)
    
def fibonacci_sequence(limit):
    res = []
    count = 0
    while True:
        num = Fibonacci(count)
        if num > limit:
            return res
        res.append(num)
        count += 1

bootstrap_servers = 'localhost:29092'
topic_name = "lab05_114547"
nMec = 114547

producer = KafkaProducer(
    bootstrap_servers=bootstrap_servers,
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

for num in fibonacci_sequence(nMec):
    message = {'nMec': str(nMec), 'generatedNumber': num, 'type': 'fibonacci'}
    producer.send(topic_name, value=message)
    print(f"Produced: {message}")

producer.close()

