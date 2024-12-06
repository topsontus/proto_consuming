package com.example.demo.deserializer;

import com.example.demo.protos.FailedPromo;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.serialization.Deserializer;

public class FailedPromoDeserializer implements Deserializer<FailedPromo> {
    @Override
    public FailedPromo deserialize(String topic, byte[] bytes) {
        try {
            return FailedPromo.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return null;
        }
    }
}