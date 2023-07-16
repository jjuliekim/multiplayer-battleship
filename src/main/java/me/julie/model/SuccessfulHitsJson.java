package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for handling successful hits
 *
 * @param coordinates of shots that hit ships on the opponent's board
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") CoordAdapter[] coordinates) {
}

