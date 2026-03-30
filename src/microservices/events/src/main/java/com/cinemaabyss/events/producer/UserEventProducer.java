package com.cinemaabyss.events.producer;

import com.cinemaabyss.events.dto.Event;
import com.cinemaabyss.events.dto.UserEvent;
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
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${kafka.producer.user.topic}")
    private String topic;

    public CompletableFuture<SendResult<String, String>> send(UserEvent event) {
        try {
            String jsonEvent = objectMapper.writeValueAsString(event);
            return kafkaTemplate.send(topic, jsonEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing user event", e);
        }
    }

    public Event createEventFromUserEvent(UserEvent userEvent) {
        Map<String, Object> payload = Map.of(
                "user_id", userEvent.getUserId(),
                "username", userEvent.getUsername(),
                "email", userEvent.getEmail() == null ? "" : userEvent.getEmail(),
                "action", userEvent.getAction()
        );
        return Event.builder()
                .id(UUID.randomUUID().toString())
                .type("user")
                .payload(payload)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
