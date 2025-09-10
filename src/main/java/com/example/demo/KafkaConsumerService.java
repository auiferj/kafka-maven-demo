package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    // This listener uses the default factory configured for String deserialization
    @KafkaListener(topics = "string-topic", groupId = "my-group-id")
    public void consumeString(String message) {
        System.out.println("Received String message: \"" + message + "\"");
    }

    // This listener uses our custom factory configured for JSON deserialization
    @KafkaListener(topics = "order-topic", groupId = "my-group-id", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrder(Order order) {
        System.out.println("Received Order message: " + order.toString());
    }
}
