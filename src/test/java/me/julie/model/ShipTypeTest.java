package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests class for ShipType
 */
public class ShipTypeTest {
  private ShipType submarine;
  private ShipType destroyer;
  private ShipType battleship;
  private ShipType carrier;

  /**
   * Sets up ships for testing
   */
  @BeforeEach
  public void setup() {
    this.submarine = ShipType.SUBMARINE;
    this.destroyer = ShipType.DESTROYER;
    this.battleship = ShipType.BATTLESHIP;
    this.carrier = ShipType.CARRIER;
  }

  /**
   * Tests submarine
   */
  @Test
  public void testSubmarine() {
    assertEquals(this.submarine.getLength(), 3);
    assertEquals(this.submarine.toString(), "SUBMARINE");
  }

  /**
   * Tests destroyer
   */
  @Test
  public void testDestroyer() {
    assertEquals(this.destroyer.getLength(), 4);
    assertEquals(this.destroyer.toString(), "DESTROYER");
  }

  /**
   * Tests battleship
   */
  @Test
  public void testBattleship() {
    assertEquals(this.battleship.getLength(), 5);
    assertEquals(this.battleship.toString(), "BATTLESHIP");
  }

  /**
   * Tests carrier
   */
  @Test
  public void testCarrier() {
    assertEquals(this.carrier.getLength(), 6);
    assertEquals(this.carrier.toString(), "CARRIER");
  }
}
