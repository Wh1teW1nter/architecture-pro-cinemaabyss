package com.cinemaabyss.events.producer;

import com.cinemaabyss.events.dto.Event;
import com.cinemaabyss.events.dto.PaymentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    @Value("${kafka.producer.payment.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CompletableFuture<SendResult<String, String>> send(PaymentEvent paymentEvent) {
        try {
            String jsonEvent = objectMapper.writeValueAsString(paymentEvent);
            return kafkaTemplate.send(topic, jsonEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing payment event", e);
        }
    }

    public Event createEventFromPaymentEvent(PaymentEvent paymentEvent) {
        Map<String, Object> payload = Map.of(
                "payment_id", paymentEvent.getPaymentId(),
                "user_id", paymentEvent.getUserId(),
                "amount", paymentEvent.getAmount(),
                "status", paymentEvent.getStatus(),
                "method_type", paymentEvent.getMethodType()
        );
        return Event.builder()
                .id(UUID.randomUUID().toString())
                .type("payment")
                .payload(payload)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
