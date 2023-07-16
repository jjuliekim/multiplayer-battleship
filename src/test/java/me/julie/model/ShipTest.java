package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for the Ship class
 */
class ShipTest {
  private Ship ship;
  private List<Coord> coords;

  /**
   * sets up a ship and its position for testing
   */
  @BeforeEach
  void setUp() {
    ship = new Ship(ShipType.SUBMARINE);
    coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 1));
    coords.add(new Coord(0, 2));
    ship.setPosition(coords);
  }

  /**
   * tests the getType method
   */
  @Test
  void getType() {
    assertEquals(ShipType.SUBMARINE, ship.getType());
  }

  /**
   * tests the setPosition method (setUp)
   */
  @Test
  void setPosition() {
    for (int i = 0; i < coords.size(); i++) {
      assertEquals(coords.get(i), ship.getPosition().get(i));
    }
  }

  /**
   * tests the getPosition method
   */
  @Test
  void getPosition() {
    for (int i = 0; i < coords.size(); i++) {
      assertEquals(coords.get(i), ship.getPosition().get(i));
    }
  }

  /**
   * tests the shipSunk method
   */
  @Test
  void shipSunk() {
    for (Coord coord : coords) {
      coord.setStatus(CellStatus.SHIP);
    }
    assertFalse(ship.shipSunk());

    for (Coord coord : coords) {
      coord.setStatus(CellStatus.HIT);
    }
    assertTrue(ship.shipSunk());
  }
}