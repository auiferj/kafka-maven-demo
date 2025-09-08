package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "my-group-id")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }
}
