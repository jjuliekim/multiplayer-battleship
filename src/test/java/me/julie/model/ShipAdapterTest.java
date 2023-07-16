package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for ShipAdapter
 */
public class ShipAdapterTest {
  private ShipAdapter adapter;
  private Ship sub;
  private ShipAdapter adapter2;
  private Ship sub2;

  /**
   * Sets up ShipAdapters for tests
   */
  @BeforeEach
  public void setup() {
    this.adapter = new ShipAdapter(new CoordAdapter(0, 0), 3, "VERTICAL");
    this.sub = new Ship(ShipType.SUBMARINE);

    ArrayList<Coord> coords = new ArrayList<>();
    Coord zeroZero = new Coord(0, 0);
    Coord zeroOne = new Coord(0, 1);
    Coord zeroTwo = new Coord(0, 2);
    coords.add(zeroZero);
    coords.add(zeroOne);
    coords.add(zeroTwo);

    this.sub.setPosition(coords);
    assertEquals(this.sub.getPosition(), coords);

    this.adapter2 = new ShipAdapter(new CoordAdapter(1, 1), 3, "HORIZONTAL");
    this.sub2 = new Ship(ShipType.SUBMARINE);
    ArrayList<Coord> coords2 = new ArrayList<>();
    coords2.add(new Coord(1, 1));
    coords2.add(new Coord(2, 1));
    coords2.add(new Coord(3, 1));
    this.sub2.setPosition(coords2);
  }

  /**
   * Tests the getDirection method
   */
  @Test
  public void testGetDirection() {
    assertEquals(this.adapter.getDirection(sub), "VERTICAL");
    assertEquals(this.adapter2.getDirection(sub2), "HORIZONTAL");
  }
}
