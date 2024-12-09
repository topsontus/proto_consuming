package com.example.demo.model;

import java.time.LocalDateTime;

public record FailedPromoRecord(Integer id, String errorType, String errorMessage,
                                Integer promocodeId, String promocodeCode, Integer promotionId,
                                String orderUuid, String userUuid, LocalDateTime createdAt,
                                LocalDateTime updatedAt) implements Record {
}
