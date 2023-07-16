package me.julie.model;

/**
 * Represents a type of Ship in a game of Battleship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int length;

  /**
   * Instantiates a new ShipType with the given length
   *
   * @param length length of the ShipType
   */
  ShipType(int length) {
    this.length = length;
  }

  /**
   * Gets the length of this ShipType
   *
   * @return length of the ShipType
   */
  public int getLength() {
    return length;
  }
}
