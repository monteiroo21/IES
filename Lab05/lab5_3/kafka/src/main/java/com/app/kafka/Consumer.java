package com.app.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @KafkaListener(topics = "lab05_114547", groupId = "myId")
    public void consume(Message message) {
        System.out.println("Received message: " + message);
    }
}
