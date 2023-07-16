package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for setting up the client
 *
 * @param fleet list of Ships in the client's fleet
 */
public record SetupClientJson(
    @JsonProperty("fleet") ShipAdapter[] fleet) {
}

