package com.example.demo.consumer;

import com.example.demo.model.FailedPromoRecord;
import com.example.demo.model.RecordMapper;
import com.example.demo.protos.FailedPromo;
import com.example.demo.repository.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FailedPromoConsumer extends BaseConsumer<FailedPromoRecord, FailedPromo> {
    @Value("${kafka.consumer.failed_promo.group.id}")
    protected String groupId;

    @Value("${kafka.consumer.failed_promo.topic}")
    protected String topic;

    public FailedPromoConsumer(Repository<FailedPromoRecord> repository, RecordMapper<FailedPromo, FailedPromoRecord> protoRecordMapper) {
        super(repository, protoRecordMapper);
    }
}
