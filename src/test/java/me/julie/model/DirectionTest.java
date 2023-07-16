package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Direction
 */
public class DirectionTest {
  private Direction vertical;
  private Direction horizontal;

  /**
   * Sets up Direction for testing
   */
  @BeforeEach
  public void setup() {
    this.vertical = Direction.VERTICAL;
    this.horizontal = Direction.HORIZONTAL;
  }

  /**
   * Tests vertical
   */
  @Test
  public void testVertical() {
    assertEquals(this.vertical.toString(), "VERTICAL");
  }

  /**
   * Tests horizontal
   */
  @Test
  public void testHorizontal() {
    assertEquals(this.horizontal.toString(), "HORIZONTAL");
  }
}
