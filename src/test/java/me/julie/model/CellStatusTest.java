package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for CellStatus
 */
public class CellStatusTest {
  private CellStatus water;
  private CellStatus ship;
  private CellStatus hit;
  private CellStatus miss;
  private CellStatus sunk;

  /**
   * Sets up CellStatus for testing
   */
  @BeforeEach
  public void setup() {
    this.water = CellStatus.WATER;
    this.ship = CellStatus.SHIP;
    this.hit = CellStatus.HIT;
    this.miss = CellStatus.MISS;
    this.sunk = CellStatus.SUNK;
  }

  /**
   * Tests water
   */
  @Test
  public void testWater() {
    assertEquals(this.water.toString(), "-");
  }

  /**
   * Tests ship
   */
  @Test
  public void testShip() {
    assertEquals(this.ship.toString(), "X");
  }

  /**
   * Tests hit
   */
  @Test
  public void testHit() {
    assertEquals(this.hit.toString(), "=");
  }

  /**
   * Tests miss
   */
  @Test
  public void testMiss() {
    assertEquals(this.miss.toString(), "O");
  }

  /**
   * Tests sunk
   */
  @Test
  public void testSunk() {
    assertEquals(this.sunk.toString(), "#");
  }
}
