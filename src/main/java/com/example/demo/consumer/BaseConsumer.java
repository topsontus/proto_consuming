package com.example.demo.consumer;

import com.example.demo.deserializer.FailedPromoDeserializer;
import com.example.demo.model.RecordMapper;
import com.example.demo.repository.Repository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class BaseConsumer<R extends Record, V> {
    private final Repository<R> repository;
    private final ExecutorService executorService;
    private KafkaConsumer<String, V> consumer;
    final RecordMapper<V, R> protoRecordMapper;
    private volatile boolean running = true;

    @Autowired
    public BaseConsumer(Repository<R> repository, RecordMapper<V, R> protoRecordMapper) {
        this.repository = repository;
        this.protoRecordMapper = protoRecordMapper;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Value("${kafka.bootstrap.servers}")
    protected String bootstrapServers;

    @Value("${kafka.consumer.failed_promo.group.id}")
    protected String groupId;

    @Value("${kafka.consumer.failed_promo.topic}")
    protected String topic;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, FailedPromoDeserializer.class.getName());

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void startConsuming() {
        while (running) {
            consumer.poll(Duration.ofSeconds(1)).forEach(record -> {
                System.out.println("received msg: " + record.toString());
                System.out.println("topic: " + record.topic());
                System.out.println("partition: " + record.partition());
                System.out.println("offset: " + record.offset());

                executorService.submit(() -> {
                    savePromo(record.value());
                });
            });
        }
    }

    private void savePromo(V promo) {
        R record = protoRecordMapper.map(promo);
        repository.create(record);
    }

    @PreDestroy
    public void cleanup() {
        running = false;
        if (consumer != null) {
            consumer.close();
        }
    }
}
