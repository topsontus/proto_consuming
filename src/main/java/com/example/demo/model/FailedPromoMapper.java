package com.example.demo.model;

import com.example.demo.protos.FailedPromo;
import org.springframework.stereotype.Component;


@Component
public class FailedPromoMapper implements RecordMapper<FailedPromo, FailedPromoRecord> {
    @Override
    public FailedPromoRecord map(FailedPromo proto) {
        return new FailedPromoRecord(null, proto.getError().getType(),
                proto.getError().getMessage(), proto.getPromocodeId(), proto.getPromocodeCode(),
                proto.getPromotionId(), proto.getOrderUuid(), proto.getUserUuid(), null, null);
    }
}
