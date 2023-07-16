package me.julie.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a Ship in a JSON-readable format
 */
public class ShipAdapter {
  public final CoordAdapter coord;
  public final int length;
  public final String direction;

  /**
   * Instantiates a new ShipAdapter with the given coord, length and direction
   *
   * @param coord starting coordinate of this Ship (topmost or leftmost coord)
   * @param length length of the ship
   * @param direction direction of the ship
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") CoordAdapter coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") String direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  /**
   * Returns the direction of the given Ship
   *
   * @param myShip Ship
   * @return direction of the ship
   */
  public String getDirection(Ship myShip) {
    if (myShip.getPosition().get(0).getX() + 1 == myShip.getPosition().get(1).getX()) {
      return Direction.HORIZONTAL.toString();
    } else {
      return Direction.VERTICAL.toString();
    }
  }
}