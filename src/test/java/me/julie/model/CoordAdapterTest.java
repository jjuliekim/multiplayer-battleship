package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests the CoordAdapter class
 */
class CoordAdapterTest {
  private CoordAdapter coordAdapter;
  private CoordAdapter coordAdapter2;

  /**
   * sets up CoordAdapters for testing
   */
  @BeforeEach
  void setUpTest() {
    coordAdapter = new CoordAdapter(1, 2);
    coordAdapter2 = new CoordAdapter(5, 3);
  }

  /**
   * tests the getX method
   */
  @Test
  void getX() {
    assertEquals(1, coordAdapter.getX());
    assertEquals(5, coordAdapter2.getX());
  }

  /**
   * tests the getY method
   */
  @Test
  void getY() {
    assertEquals(2, coordAdapter.getY());
    assertEquals(3, coordAdapter2.getY());
  }
}