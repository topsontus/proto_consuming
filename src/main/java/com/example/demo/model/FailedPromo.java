package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "failed_promos")
@Getter
@Setter
public class FailedPromo extends BaseEntity {
    @Column(name = "error_type")
    @NotBlank
    private String errorType;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "promocode_id")
    @NotNull
    private Integer promocodeId;

    @Column(name = "promocode_code")
    @NotBlank
    private String promocodeCode;

    @Column(name = "promotion_id")
    @NotNull
    private Integer promotionId;

    @Column(name = "order_uuid")
    @NotBlank
    private String orderUuid;

    @Column(name = "user_uuid")
    @NotBlank
    private String userUuid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
