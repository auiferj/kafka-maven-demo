package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String STRING_TOPIC = "string-topic";
    private static final String ORDER_TOPIC = "order-topic";

    // Endpoint to send a simple string message
    @GetMapping("/publish/string")
    public String publishStringMessage(@RequestParam("message") String message) {
        kafkaTemplate.send(STRING_TOPIC, message);
        return "String message sent to the Kafka topic: " + STRING_TOPIC;
    }

    // Endpoint to send an Order object
    @PostMapping("/publish/order")
    public String publishOrderMessage(@RequestBody Order order) {
        kafkaTemplate.send(ORDER_TOPIC, order.getOrderId(), order);
        return "Order sent to the Kafka topic: " + ORDER_TOPIC;
    }
}
