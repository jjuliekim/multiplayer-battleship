package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for the Coord class
 */
class CoordTest {
  private Coord coord;
  private Coord coord2;

  /**
   * initializes the Coord objects for testing
   */
  @BeforeEach
  void setUpTest() {
    coord = new Coord(1, 2, CellStatus.WATER);
    coord2 = new Coord(5, 4, CellStatus.HIT);
  }

  /**
   * tests the getY method
   */
  @Test
  void getY() {
    assertEquals(2, coord.getY());
    assertEquals(4, coord2.getY());
  }

  /**
   * tests the getX method
   */
  @Test
  void getX() {
    assertEquals(1, coord.getX());
    assertEquals(5, coord2.getX());
  }

  /**
   * tests the getStatus method
   */
  @Test
  void getStatus() {
    assertEquals(CellStatus.WATER, coord.getStatus());
    assertEquals(CellStatus.HIT, coord2.getStatus());
  }

  /**
   * tests the setStatus method
   */
  @Test
  void setStatus() {
    coord.setStatus(CellStatus.HIT);
    assertEquals(CellStatus.HIT, coord.getStatus());
    coord2.setStatus(CellStatus.SUNK);
    assertEquals(CellStatus.SUNK, coord2.getStatus());
  }

  /**
   * tests the equals method
   */
  @Test
  void testEquals() {
    Coord coord3 = new Coord(1, 2, CellStatus.WATER);
    Coord coord4 = new Coord(1, 4, CellStatus.WATER);
    assertTrue(coord.equals(coord3));
    assertFalse(coord.equals(coord2));
    assertFalse(coord.equals(coord4));
  }
}