package com.org.ps.martek.service;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.google.gson.Gson;
import com.org.ps.martek.dto.ProductSearch;

public class KafkaService {
    private final String topic;
    private final Properties props;
    private static int i;

    public KafkaService(String brokers, String username, String password) {
        this.topic = username + "-default";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", username + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);

        /*configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ProductSearch.class);
        configProps.put(
                JsonDeserializer.TRUSTED_PACKAGES, 
                "*");*/
    }

    public void consume() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("%s [%d] offset=%d, key=%s, value=\"%s\"\n",
								  record.topic(), record.partition(),
								  record.offset(), record.key(), record.value());
                ProductSearch pro =new Gson().fromJson(record.value(), ProductSearch.class);
                System.out.println(pro.getFlowId());
			}
        }
    }

    public void produce(ProductSearch product) {
                try (Producer<String, String> producer = new KafkaProducer<>(props)) {
                    String st= new Gson().toJson(product);
                        producer.send(new ProducerRecord<>(topic, Integer.toString(i), st));
                     i++;   
                } catch(Exception e ) {
    }
    }
}
