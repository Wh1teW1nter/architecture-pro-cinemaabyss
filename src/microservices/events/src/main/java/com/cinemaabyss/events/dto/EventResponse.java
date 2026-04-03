package com.cinemaabyss.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventResponse {

    @JsonProperty("status")
    private String status = "success";

    @JsonProperty("partition")
    private Integer partition;

    @JsonProperty("offset")
    private Long offset;

    @JsonProperty("event")
    private Event event;
}
