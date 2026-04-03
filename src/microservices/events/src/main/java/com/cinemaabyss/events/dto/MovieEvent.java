package com.cinemaabyss.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class MovieEvent {

    @JsonProperty("movie_id")
    private Integer movieId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("action")
    private String action;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("rating")
    private BigDecimal rating;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("description")
    private String description;
}

