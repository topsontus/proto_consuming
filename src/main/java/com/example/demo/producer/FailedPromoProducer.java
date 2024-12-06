package com.example.demo.producer;

import com.example.demo.protos.FailedPromo;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class FailedPromoProducer {
    private KafkaProducer<String, byte[]> producer;

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.failed_promo.topic}")
    private String topic;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    public void produceMsg() {
        FailedPromo.Error error = FailedPromo.Error.newBuilder().setMessage("kek").setType("kek-type").build();

        FailedPromo message = FailedPromo.newBuilder()
                .setError(error)
                .setPromocodeId(321)
                .setPromocodeCode("aye")
                .setPromotionId(123)
                .setOrderUuid("aye")
                .setUserUuid("aye")
                .build();

        byte[] msg = message.toByteArray();

        producer.send(new ProducerRecord<>(topic, msg));
        producer.close();
    }
}
