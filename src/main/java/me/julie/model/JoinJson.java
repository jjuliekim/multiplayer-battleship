package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for joining a game
 *
 * @param name of this player
 * @param gameType type of game (single or multi)
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {
}
