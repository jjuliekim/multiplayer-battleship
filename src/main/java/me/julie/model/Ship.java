package me.julie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Ship in a game of Battleship
 */
public class Ship {
  private final ShipType type;
  private List<Coord> position = new ArrayList<>();

  /**
   * Instantiates a new Ship of the given type
   *
   * @param type type of Ship
   */
  public Ship(ShipType type) {
    this.type = type;
  }

  /**
   * Gets the type of this Ship
   *
   * @return the type of this Ship
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Sets the position of this Ship
   *
   * @param coords list of Coords that this ship occupies
   */
  public void setPosition(List<Coord> coords) {
    position = coords;
  }

  /**
   * Gets the position of this ship
   *
   * @return list of coords that this ship occupies
   */
  public List<Coord> getPosition() {
    return position;
  }

  /**
   * Returns whether this ship is sunk
   *
   * @return true if the ship has sunk, false if it has not sunk
   */
  public boolean shipSunk() {
    boolean sunk = false;
    for (Coord coord : position) {
      if (coord.getStatus().equals(CellStatus.HIT)) {
        sunk = true;
      } else {
        return false;
      }
    }
    if (sunk) {
      for (Coord cell : position) {
        cell.setStatus(CellStatus.SUNK);
      }
    }
    return sunk;
  }
}
