package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for handling endgame
 *
 * @param result Result of the game (Win, Loss or Draw)
 * @param reason for the game ending
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {
}

