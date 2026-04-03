package com.cinemaabyss.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorEvent {
        @JsonProperty("error")
        private String error;

        @JsonProperty("code")
        private Integer code;
}
