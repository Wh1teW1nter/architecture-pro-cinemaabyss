package com.cinemaabyss.events.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieEventListener {

    @KafkaListener(topics = "${kafka.consumer.movie.topic}")
    public void consumeMovieEvent(String movieEventJson) {
        log.info("Received Movie event: {}", movieEventJson);
    }
}
