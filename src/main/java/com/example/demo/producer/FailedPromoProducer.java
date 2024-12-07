package com.example.demo.producer;

import com.example.demo.model.FailedPromoRecord;
import com.example.demo.protos.FailedPromo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    public void produceMsg(FailedPromoRecord failedPromoRecord) throws IOException, ProduceMessageException {
        FailedPromo.Error error = FailedPromo.Error.newBuilder().setMessage(failedPromoRecord.errorMessage()).setType(failedPromoRecord.errorType()).build();

        FailedPromo message = FailedPromo.newBuilder()
                .setError(error)
                .setPromocodeId(failedPromoRecord.promocodeId())
                .setPromocodeCode(failedPromoRecord.promocodeCode())
                .setPromotionId(failedPromoRecord.promocodeId())
                .setOrderUuid(failedPromoRecord.orderUuid())
                .setUserUuid(failedPromoRecord.userUuid())
                .build();

        byte[] msg = message.toByteArray();

        try {
            producer.send(new ProducerRecord<>(topic, msg));
        } catch (KafkaException e) {
            throw new ProduceMessageException(e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (producer != null) {
            producer.close();
        }
    }
}
