package com.cinemaabyss.events.controller;

import com.cinemaabyss.events.dto.*;
import com.cinemaabyss.events.producer.MovieEventProducer;
import com.cinemaabyss.events.producer.PaymentEventProducer;
import com.cinemaabyss.events.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {

    private final UserEventProducer userProducer;
    private final PaymentEventProducer paymentProducer;
    private final MovieEventProducer movieProducer;


    @GetMapping("/health")
    public ResponseEntity<Map<String, Boolean>> getEventsServiceHealth() {
        log.info("Health check requested for Events Service");
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUserEvent(@RequestBody UserEvent userEvent) {
        try {
            SendResult<String, String> result = userProducer.send(userEvent).get();
            RecordMetadata metadata = result.getRecordMetadata();
            Event event = userProducer.createEventFromUserEvent(userEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse(metadata, event));
        } catch (InterruptedException | ExecutionException e) {
            Error error = new Error("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("ERROR:", e);

            return ResponseEntity.badRequest()
                    .body(ErrorEvent.builder().code(HttpStatus.BAD_REQUEST.value()).error(e.getMessage()).build());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> createPaymentEvent(@RequestBody PaymentEvent paymentEvent) {
        try {
            SendResult<String, String> result = paymentProducer.send(paymentEvent).get();
            RecordMetadata metadata = result.getRecordMetadata();
            Event event = paymentProducer.createEventFromPaymentEvent(paymentEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse(metadata, event));
        } catch (InterruptedException | ExecutionException e) {
            Error error = new Error("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            Error error = new Error("Invalid request: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/movie")
    public ResponseEntity<?> createMovieEvent(@RequestBody MovieEvent movieEvent) {
        try {
            SendResult<String, String> result = movieProducer.send(movieEvent).get();
            RecordMetadata metadata = result.getRecordMetadata();
            Event event = movieProducer.createEventFromMovieEvent(movieEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse(metadata, event));
        } catch (InterruptedException | ExecutionException e) {
            Error error = new Error("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            Error error = new Error("Invalid request: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


    private EventResponse createResponse(RecordMetadata metadata, Event event) {
        return EventResponse.builder()
                .status("success")
                .partition(metadata.partition())
                .offset(metadata.offset())
                .event(event)
                .build();
    }

}
