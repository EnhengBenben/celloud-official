package com.celloud.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.exception.BusinessException;

public class MessageReceiver implements Runnable {
	private final AtomicBoolean running = new AtomicBoolean(false);
	private static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
	private Consumer<String, String> consumer;
	private static Map<String, MessageReceiver> maps = new ConcurrentHashMap<>();
	private static MessageHandler defaultHandler = new DefaultMessageHandler();
	private MessageHandler handler = null;
	private String group;
	private List<String> topics = new ArrayList<>();

	private MessageReceiver() {
	}

	public static synchronized MessageReceiver getInstance(String group) {
		if (maps.containsKey(group)) {
			return maps.get(group);
		}
		MessageReceiver receiver = new MessageReceiver();
		receiver.group = group;
		maps.put(group, receiver);
		return receiver;
	}

	public MessageReceiver subscribe(String... topics) {
		return subscribe(defaultHandler, topics);
	}

	public MessageReceiver subscribe(MessageHandler handler, String... topics) {
		if (handler == null) {
			throw new RuntimeException("message handler must be setted first.");
		}
		if (topics == null || topics.length == 0) {
			throw new RuntimeException("there is no topic to subscribe.");
		}
		this.handler = handler;
		running.set(true);
		this.topics.addAll(Arrays.asList(topics));
		return this;
	}

	public void run() {
		logger.debug("topics = {}", topics);
		if (consumer != null) {
			logger.info("consumer is already in use !");
			consumer.close();
		}
		consumer = new KafkaConsumer<>(KafkaUtils.getConsumerProps(group));
		consumer.subscribe(topics);
		try {
			while (running.get()) {
				logger.debug("receiving...");
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					logger.info("topic = {}, offset = {}, key = {}, value = {}", record.topic(), record.offset(),
							record.key(), record.value());
					try {
						handler.handle(record.key(), record.value());
					} catch (Exception e) {
						logger.error("消息处理失败。。。", e);
					}
				}
			}
		} catch (Exception e) {
			if (running.get()) {
				throw new BusinessException("kafka消费者异常！", e);
			}
			shutdown();
		} finally {
			consumer.close();
		}

	}

	public void addTopic(String topic) {
		consumer.subscribe(Arrays.asList(topic));
	}

	public void shutdown() {
		running.set(false);
		consumer.wakeup();
	}

	public boolean isRunning() {
		return running.get();
	}

}
