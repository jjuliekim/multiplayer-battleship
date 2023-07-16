package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Represents a Json for setting up the server
 *
 * @param width width of the board
 * @param height height of the board
 * @param fleetSpec types of ships in the fleet
 */
public record SetupServerJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
}
