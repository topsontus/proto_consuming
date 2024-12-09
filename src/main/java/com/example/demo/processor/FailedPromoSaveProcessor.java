package com.example.demo.processor;

import com.example.demo.deserializer.FailedPromoDeserializer;
import com.example.demo.model.FailedPromoMapper;
import com.example.demo.model.FailedPromoRecord;
import com.example.demo.protos.FailedPromo;
import com.example.demo.repository.FailedPromoRepository;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Component
public class FailedPromoSaveProcessor {

    @Value("${kafka.consumer.failed_promo.topic}")
    private String topic;

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder, FailedPromoRepository repository, FailedPromoDeserializer deserializer) {
        KStream<String, Bytes> messageStream = streamsBuilder
                .stream(topic, Consumed.with(Serdes.String(), Serdes.Bytes()));

        messageStream.foreach((k, promo) -> {
            FailedPromo failedPromo = deserializer.deserialize(topic, promo.get());
            FailedPromoRecord record = FailedPromoMapper.INSTANCE.failedPromoProtoToFailedPromoRecord(failedPromo);

            CompletableFuture.runAsync(() -> {
                try {
                    repository.create(record);
                } catch (SQLException e) {
                    // send to dead letter queue
                }
            });
        });
    }
}
