package com.example.demo.consumer;

import com.example.demo.deserializer.FailedPromoDeserializer;
import com.example.demo.model.FailedPromoMapper;
import com.example.demo.model.FailedPromoRecord;
import com.example.demo.protos.FailedPromo;
import com.example.demo.repository.FailedPromoRepository;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class FailedPromoConsumer extends BaseKafkaConsumer<String, FailedPromo> {

    private final FailedPromoRepository repository;
    private final String groupId;

    @Autowired
    public FailedPromoConsumer(
            @Value("${kafka.consumer.failed_promo.topic}") String topic,
            @Value("${kafka.consumer.pollTimeout}") int pollTimeout,
            @Value("${kafka.consumer.failed_promo.group.id}") String groupId,
            FailedPromoRepository repository
    ) {
        super(topic, pollTimeout);
        this.repository = repository;
        this.groupId = groupId;
    }

    @Override
    void consumeMessage(FailedPromo message) {
        FailedPromoRecord record = FailedPromoMapper.INSTANCE.failedPromoProtoToFailedPromoRecord(message);

        try {
            repository.create(record);
        } catch (SQLException e) {
            System.out.println("Failed to record message");
            // save to dead letter queue
        }
    }

    @PostConstruct
    void prepareConsumerProperties() {
        this.consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, FailedPromoDeserializer.class.getName());
    }
}
