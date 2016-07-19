package com.celloud.message.web;

import java.util.Map;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MessageSender {
    private static Producer<String, String> producer = KafkaUtils.createProducer();

    public static void send(String topic, String key, String message) {
        producer.send(new ProducerRecord<String, String>(topic, key, message));
        producer.flush();
    }

    public static void send(String topic, Map<String, String> messages) {
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            producer.send(new ProducerRecord<String, String>(topic, entry.getKey(), entry.getValue()));
        }
        producer.flush();
    }

    public static void shutdown() {
        producer.close();
    }

}
