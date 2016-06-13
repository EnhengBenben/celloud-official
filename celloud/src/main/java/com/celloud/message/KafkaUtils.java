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
    private static Properties props = ConstantsData.loadProperties(KAFKA_PROPERTIES_PATH);
    private static String defaultGroupId = "celloud-task-message";
    private static AtomicBoolean useKafka = null;
    private static Properties consumerProps = null;
    private static Properties producerProps = null;

    public static Consumer<String, String> createConsumer(String group) {
        if (consumerProps == null) {
            consumerProps = new Properties();
            for (String key : props.stringPropertyNames()) {
                if (key.startsWith("public.")) {
                    consumerProps.put(key.substring("public.".length()), props.get(key));
                } else if (key.startsWith("consumer.")) {
                    consumerProps.put(key.substring("consumer.".length()), props.get(key));
                }
            }
            String groupId = consumerProps.getProperty("group.id");
            if (!StringUtils.isBlank(groupId)) {
                defaultGroupId = groupId.trim();
            }
        }
        if (StringUtils.isBlank(group)) {
            group = defaultGroupId;
        }
        consumerProps.put("group.id", group);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        return consumer;
    }

    public static Producer<String, String> createProducer() {
        if (producerProps == null) {
            producerProps = new Properties();
            for (String key : props.stringPropertyNames()) {
                if (key.startsWith("public.")) {
                    producerProps.put(key.substring("public.".length()), props.get(key));
                } else if (key.startsWith("producer.")) {
                    producerProps.put(key.substring("producer.".length()), props.get(key));
                }
            }
        }
        return new KafkaProducer<>(producerProps);
    }

    public static void closeProducer(Producer<String, String> producer) {
        producer.close();
    }

    public static void closeConsumer(Consumer<String, String> consumer) {
        consumer.close();
    }

    public static boolean useKafka() {
        if (useKafka != null) {
            return useKafka.get();
        }
        useKafka = new AtomicBoolean(true);
        try {
            String kafkaOpen = props.getProperty("kafka.open");
            if (!"true".equals(kafkaOpen.trim().toLowerCase())) {
                useKafka.set(false);
            }
        } catch (Exception e) {
        }
        return useKafka.get();
    }

    public static void main(String[] args) {
    }
}
