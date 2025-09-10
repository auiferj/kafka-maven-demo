package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    /**
     * 注入Kafka的标准配置
     * Spring Boot 会自动读取 application.properties 文件中所有以 spring.kafka 开头的属性，并将它们封装到这个
     *       KafkaProperties 对象中。这样我们就可以轻松地复用像 bootstrap-servers、group-id 这样的通用配置，而无需在代码里硬编码。
     */
    @Autowired
    private KafkaProperties kafkaProperties;

    // This factory is for consuming Order objects
    // 可定制的 Kafka 监听器容器工厂
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Order> orderKafkaListenerContainerFactory() {
        // 获取application.properties中的通用消费者配置
        // kafkaProperties是在SpringBoot启动时创建的。这里调用了它的buildConsumerProperties方法获取消费者相关的Map结构的属性
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        // 创建并配置JsonDeserializer,Configure the JsonDeserializer for the Order class
        JsonDeserializer<Order> jsonDeserializer = new JsonDeserializer<>(Order.class, false);
        jsonDeserializer.addTrustedPackages("*"); // Trust all packages

        //创建一个消费者工厂
        ConsumerFactory<String, Order> consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, //通用配置
                new StringDeserializer(), //Key的反序列化器
                jsonDeserializer //Value的反序列化器（我们定制的）
        );

        //创建最终的监听器工厂
        ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);//将消费者工程设置进去
        return factory;//将这个定制工厂返回给Spring
    }
}
