package me.julie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Json for reporting damage
 *
 * @param coordinates of shots that hits ships on the player's board
 */
public record ReportDamageClientJson(
    @JsonProperty("coordinates") CoordAdapter[] coordinates) {
}
