package com.cinemaabyss.proxy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController("/api")
public class HealthController {

    @GetMapping("/health")
    public Mono<Map<String, Boolean>> health() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", true);
        return Mono.just(response);
    }
}
