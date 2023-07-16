package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for taking shots
 *
 * @param coordinates of shots that the player is shooting in this turn
 */
public record TakeShotsJson(
    @JsonProperty("coordinates") CoordAdapter[] coordinates) {
}