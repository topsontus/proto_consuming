package com.example.demo.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FailedPromoCreationForm {
    @NotBlank(message = "Please provide a errorType")
    @JsonProperty("error_type")
    private String errorType;

    @JsonProperty("error_message")
    private String errorMessage;

    @NotNull(message = "Please provide a promocodeId")
    @JsonProperty("promocode_id")
    private Integer promocodeId;

    @NotBlank(message = "Please provide a promocodeCode")
    @JsonProperty("promocode_code")
    private String promocodeCode;

    @NotNull(message = "Please provide a promotionId")
    @JsonProperty("promotion_id")
    private Integer promotionId;

    @NotBlank(message = "Please provide a orderUuid")
    @JsonProperty("order_uuid")
    private String orderUuid;

    @NotBlank(message = "Please provide a userUuid")
    @JsonProperty("user_uuid")
    private String userUuid;
}
