package com.celloud.message;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import com.celloud.constants.ConstantsData;

public class KafkaUtils {
	private static final String KAFKA_PROPERTIES_PATH = "kafka.properties";
	private static Properties props = null;
	private static String defaultGroupId = "celloud-task-message";
	private static AtomicBoolean useKafka = null;
	private static Properties consumerProps = null;
	private static Properties producerProps = null;

	public static Consumer<String, String> createConsumer(String group) {
		loadProps();
		if (StringUtils.isBlank(group)) {
			group = defaultGroupId;
		}
		Properties pro = copyProperties(consumerProps);
		pro.put("group.id", group);
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(pro);
		return consumer;
	}

	public static Producer<String, String> createProducer() {
		loadProps();
		return new KafkaProducer<>(producerProps);
	}

	public static void closeProducer(Producer<String, String> producer) {
		producer.close();
	}

	public static void closeConsumer(Consumer<String, String> consumer) {
		consumer.close();
	}

	public static boolean useKafka() {
		loadProps();
		return useKafka.get();
	}

	private static synchronized void loadProps() {
		if (consumerProps == null || producerProps == null || useKafka == null) {
			props = ConstantsData.loadProperties(KAFKA_PROPERTIES_PATH);
			consumerProps = new Properties();
			producerProps = new Properties();
			for (String key : props.stringPropertyNames()) {
				if (key.startsWith("public.")) {
					String pro = key.substring("public.".length());
					Object value = props.get(key);
					consumerProps.put(pro, value);
					producerProps.put(pro, value);
				} else if (key.startsWith("consumer.")) {
					consumerProps.put(key.substring("consumer.".length()), props.get(key));
				} else if (key.startsWith("producer.")) {
					producerProps.put(key.substring("producer.".length()), props.get(key));
				}
			}
			String groupId = consumerProps.getProperty("group.id");
			if (!StringUtils.isBlank(groupId)) {
				defaultGroupId = groupId.trim();
			}
			useKafka = new AtomicBoolean(true);
			try {
				String kafkaOpen = props.getProperty("kafka.open");
				if (!"true".equals(kafkaOpen.trim().toLowerCase())) {
					useKafka.set(false);
				}
			} catch (Exception e) {
			}
		}
	}

	private static Properties copyProperties(Properties props) {
		Properties p = new Properties();
		for (String key : props.stringPropertyNames()) {
			p.put(key, props.get(key));
		}
		return p;
	}

	public static Properties getConsumerProps(String groupId) {
		loadProps();
		Properties pro = copyProperties(consumerProps);
		pro.put("group.id", groupId);
		return pro;
	}

	public static Properties getProducerProps() {
		loadProps();
		return producerProps;
	}
}
