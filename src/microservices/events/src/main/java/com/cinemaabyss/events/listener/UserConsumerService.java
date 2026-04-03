package com.cinemaabyss.events.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConsumerService {
    @KafkaListener(topics = "${kafka.consumer.user.topic}")
    public void consumeUserEvent(String userEventJson) {
        log.info("Received User event: {}", userEventJson);
    }
}