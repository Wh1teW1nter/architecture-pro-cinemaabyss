package com.cinemaabyss.events.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventListener {

    @KafkaListener(topics = "${kafka.consumer.payment.topic}")
    public void listen(String paymentEventJson) {
        log.info("Received Payment event: {}", paymentEventJson);
    }
}
