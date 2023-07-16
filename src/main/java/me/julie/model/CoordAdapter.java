package me.julie.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a Coord in a Json-readable format
 */
public class CoordAdapter {
  private final int coordX;
  private final int coordY;

  /**
   * Instantiates a new CoordAdapter with the given x and y
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  @JsonCreator
  public CoordAdapter(
      @JsonProperty("x") int x,
      @JsonProperty("y") int y) {
    this.coordX = x;
    this.coordY = y;
  }

  /**
   * Gets the x of this CoordAdapter
   *
   * @return x-coordinate of this CoordAdapter
   */
  public int getX() {
    return this.coordX;
  }

  /**
   * Gets the y of this CoordAdapter
   *
   * @return y-coordinate of this CoordAdapter
   */
  public int getY() {
    return this.coordY;
  }

}
