package com.cinemaabyss.events.producer;

import com.cinemaabyss.events.dto.Event;
import com.cinemaabyss.events.dto.MovieEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MovieEventProducer {

    @Value("${kafka.producer.movie.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CompletableFuture<SendResult<String, String>> send(MovieEvent movieEvent) {
        try {
            String eventJson = objectMapper.writeValueAsString(movieEvent);
            return kafkaTemplate.send(topic, eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing movie event", e);
        }
    }

    public Event createEventFromMovieEvent(MovieEvent movieEvent) {
        Map<String, Object> payload = Map.of(
                "movie_id", movieEvent.getMovieId(),
                "title", movieEvent.getTitle(),
                "action", movieEvent.getAction(),
                "user_id", movieEvent.getUserId(),
                "rating", movieEvent.getRating() ==  null ? 0 : movieEvent.getRating(),
                "genres", movieEvent.getGenres() == null ? Collections.emptyList() : movieEvent.getGenres(),
                "description", movieEvent.getDescription() == null ? "" : movieEvent.getDescription()
        );

        return Event.builder()
                .id(UUID.randomUUID().toString())
                .type("movie")
                .payload(payload)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}