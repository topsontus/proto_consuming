package com.example.demo.consumer;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class BaseKafkaConsumer<K, V> {
    private volatile boolean running = true;
    private KafkaConsumer<K, V> consumer;

    @Autowired
    @Qualifier("consumerProperties")
    protected Properties consumerProperties;

    private final ExecutorService executorService;
    private final int pollTimeout;
    private final String topic;

    public BaseKafkaConsumer(String topic, int pollTimeout) {
        this.executorService = Executors.newCachedThreadPool();
        this.topic = topic;
        this.pollTimeout = pollTimeout;
    }

    public void startConsuming() {
        consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Collections.singletonList(topic));

        while (running) {
            consumer.poll(Duration.ofSeconds(pollTimeout)).forEach(record -> {
                executorService.submit(() -> {
                    consumeMessage(record.value());
                });
            });
        }
    }

    abstract void consumeMessage(V message);

    @PreDestroy
    public void cleanup() {
        running = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        if (consumer != null) {
            consumer.close();
        }
    }
}
