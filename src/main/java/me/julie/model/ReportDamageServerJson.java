package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for reporting damage
 *
 * @param coordinates of shots that were launched at the player
 */
public record ReportDamageServerJson(
    @JsonProperty("coordinates") CoordAdapter[] coordinates) {
}
