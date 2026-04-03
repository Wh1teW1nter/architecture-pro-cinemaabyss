package com.cinemaabyss.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Builder
public class PaymentEvent {


    @JsonProperty("payment_id")
    private Integer paymentId;

    @JsonProperty("user_id")
    private Integer userId;


    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime timestamp;

    @JsonProperty("method_type")
    private String methodType;
}