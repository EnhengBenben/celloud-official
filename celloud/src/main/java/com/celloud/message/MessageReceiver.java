package com.celloud.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private Consumer<String, String> consumer;
    private static Map<String, MessageReceiver> maps = new ConcurrentHashMap<>();
    private static MessageHandler defaultHandler = new DefaultMessageHandler();
    private MessageHandler handler = null;
    private boolean running = false;
    private List<String> topics = new ArrayList<>();

    private MessageReceiver() {
    }

    public static MessageReceiver getInstance(String group) {
        if (maps.containsKey(group)) {
            return maps.get(group);
        }
        MessageReceiver receiver = new MessageReceiver();
        receiver.setConsumer(KafkaUtils.createConsumer(group));
        maps.put(group, receiver);
        return receiver;
    }

    public void subscribe(String... topics) {
        subscribe(defaultHandler, topics);
    }

    public void subscribe(MessageHandler handler, String... topics) {
        if (consumer == null) {
            throw new RuntimeException("consumer must be setted first.");
        }
        if (handler == null) {
            throw new RuntimeException("message handler must be setted first.");
        }
        if (topics == null || topics.length == 0) {
            throw new RuntimeException("there is no topic to subscribe.");
        }
        this.handler = handler;
        this.topics.addAll(Arrays.asList(topics));
        running = true;
        run();
    }

    private void run() {
        logger.debug("topics = {}", topics);
        consumer.subscribe(topics);
        while (running) {
            logger.debug("receiving...");
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                logger.info("topic = {}, offset = {}, key = {}, value = {}", record.topic(), record.offset(),
                        record.key(), record.value());
                try {
                    handler.handle(record.key(), record.value());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addTopic(String topic) {
        consumer.subscribe(Arrays.asList(topic));
    }

    @SuppressWarnings("unused")
    private void shutdown() {
        running = false;
        consumer.close();
    }

    private void setConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public boolean isRunning() {
        return this.running;
    }

}
