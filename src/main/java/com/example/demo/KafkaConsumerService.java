package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 这个类扮演了一个自动化的消息订阅者的角色。你不需要手动去连接 Kafka、拉取消息、处理循环，Spring Boot 和 spring-kafka 框架已经帮你把所有这些繁重的工作都做好了
 */
@Service
public class KafkaConsumerService {

    /**
     * @KafkaListener
     * 声明这是一个 Kafka 监听器方法
     * @KafkaListener它来自于 spring-kafka 库
     * 作用: 这个注解告诉 Spring：“请把下面这个 consume 方法注册为一个 Kafka 消息的监听器。”
     *    * 工作原理:
     *        * 在应用启动后，Spring Kafka 会在后台自动创建一个 Kafka 消费者客户端。
     *        * 这个客户端会连接到你在 application.properties 中配置的 Kafka 服务器 (localhost:9092)。
     *        * 然后，它会根据 @KafkaListener 注解的参数，自动订阅指定的 Topic。
     *        * 它会在一个独立的线程中自动地、持续地运行一个 while(true) 循环来调用 consumer.poll() 拉取消息。这是你手动编写消费者时最核心的循环，但现在框架帮你做了！
     *        * 一旦从 Kafka 拉取到新消息，框架会自动调用被 @KafkaListener 标记的 consume 方法，并将消息内容作为参数传进来。
     *     * 注解的参数:
     *        * topics = "test-topic": 这非常直白，它指定了要监听（订阅）的 Topic 的名字。你可以监听一个或多个 Topic (例如 topics = {"topic1", "topic2"})。
     *        * groupId = "my-group-id": 这是 Kafka 的一个核心概念——消费者组 (Consumer Group)。
     *            * 作用: 它用来标识一组共同消费一个 Topic 的消费者。
     *            * 关键特性: Kafka 会保证，发布到 Topic 中的每一条消息，只会被同一个消费者组中的一个消费者实例所消费。
     *            * 举例: 如果你把这个应用打包后，启动了 3 个实例（3 个进程），那么这 3 个实例都属于 "my-group-id" 这个组。当有 3 条消息进来时，Kafka 可能会把第一条消息给实例 A，第二条给实例
     *              B，第三条给实例 C。这样就实现了消费的负载均衡和水平扩展。如果只有一个实例，那么所有的消息都由它来处理。
     *
     * consume(String message)
     * 参数 (`String message`):
     * 这是消息的内容 (Payload)。spring-kafka 框架非常智能，它会自动将从 Kafka Topic
     *      中收到的原始字节数据进行反序列化。因为我们的生产者发送的是简单的字符串，所以这里可以直接声明一个 String 类型的参数来接收它。如果生产者发送的是 JSON，你甚至可以定义一个对应的 Java
     *      对象（POJO）作为参数，框架会自动帮你完成 JSON 到对象的转换。
     *
     * 方法体
     * 在真实的应用中，这里可能会是：
     *        * 将消息内容保存到数据库。
     *        * 调用另一个微服务。
     *        * 进行一些复杂的计算。
     *        * 发送一封邮件等等。
     * @param message
     */
    @KafkaListener(topics = "test-topic", groupId = "my-group-id")
    public void consume(String message) {
        // 6. 处理消息的业务逻辑
        System.out.println("Received message: " + message);
    }
}
