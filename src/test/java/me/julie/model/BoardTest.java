package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for the Board class
 */
class BoardTest {
  private Board board;

  /**
   * sets up a board before each test
   */
  @BeforeEach
  void setupTest() {
    board = new Board(7, 8);
  }

  /**
   * tests the getHeight method in Board
   */
  @Test
  void getHeight() {
    assertEquals(7, board.getHeight());
  }

  /**
   * tests the getWidth method in Board
   */
  @Test
  void getWidth() {
    assertEquals(8, board.getWidth());
  }

  /**
   * tests the getCells method in Board
   */
  @Test
  void getCells() {
    Coord[][] cells = new Coord[8][7];
    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        cells[i][j] = new Coord(i, j, CellStatus.WATER);
      }
    }
    assertEquals(7, board.getCells().length);
    assertEquals(CellStatus.WATER, board.getCells()[0][0].getStatus());
  }

  /**
   * tests the setCell method in Board
   */
  @Test
  void setCell() {
    board.setCell(new Coord(0, 1, CellStatus.WATER));
    assertEquals(CellStatus.WATER, board.getCells()[0][1].getStatus());
    assertEquals(1, board.getCells()[0][1].getX());
    assertEquals(0, board.getCells()[0][1].getY());
  }

  /**
   * tests the placeShip method for a carrier
   */
  @Test
  void placeCarrier() {
    Random random = new Random(1);
    Ship ship = new Ship(ShipType.CARRIER);
    board = new Board(6, 8);
    board.placeShip(ship, random);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 1));
    coords.add(new Coord(0, 2));
    coords.add(new Coord(0, 3));
    coords.add(new Coord(0, 4));
    coords.add(new Coord(0, 5));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[1][1].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[4][0].getStatus());

    ship = new Ship(ShipType.CARRIER);
    board = new Board(8, 6);
    board.placeShip(ship, random);
    coords = new ArrayList<>();
    coords.add(new Coord(0, 3));
    coords.add(new Coord(1, 3));
    coords.add(new Coord(2, 3));
    coords.add(new Coord(3, 3));
    coords.add(new Coord(4, 3));
    coords.add(new Coord(5, 3));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[2][2].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[3][1].getStatus());
  }

  /**
   * tests the placeShip method for a battleship
   */
  @Test
  void placeBattleship() {
    Random random = new Random(1);
    Ship ship = new Ship(ShipType.BATTLESHIP);
    board.placeShip(ship, random);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(3, 0));
    coords.add(new Coord(3, 1));
    coords.add(new Coord(3, 2));
    coords.add(new Coord(3, 3));
    coords.add(new Coord(3, 4));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[1][0].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[0][3].getStatus());

    ship = new Ship(ShipType.BATTLESHIP);
    board.placeShip(ship, random);
    coords = new ArrayList<>();
    coords.add(new Coord(1, 6));
    coords.add(new Coord(2, 6));
    coords.add(new Coord(3, 6));
    coords.add(new Coord(4, 6));
    coords.add(new Coord(5, 6));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[2][2].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[6][2].getStatus());
  }

  /**
   * tests the placeShip method for a destroyer
   */
  @Test
  void placeDestroyer() {
    Random random = new Random(1);
    Ship ship = new Ship(ShipType.DESTROYER);
    board.placeShip(ship, random);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(3, 1));
    coords.add(new Coord(3, 2));
    coords.add(new Coord(3, 3));
    coords.add(new Coord(3, 4));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[1][0].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[1][3].getStatus());

    ship = new Ship(ShipType.DESTROYER);
    board.placeShip(ship, random);
    coords = new ArrayList<>();
    coords.add(new Coord(0, 6));
    coords.add(new Coord(1, 6));
    coords.add(new Coord(2, 6));
    coords.add(new Coord(3, 6));
    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[2][2].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[6][0].getStatus());
  }


  /**
   * tests the placeShip method for a submarine
   */
  @Test
  void placeSubmarine() {
    Random random = new Random(1);
    Ship ship = new Ship(ShipType.SUBMARINE);
    board.placeShip(ship, random);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(3, 0));
    coords.add(new Coord(3, 1));
    coords.add(new Coord(3, 2));

    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    assertEquals(CellStatus.WATER, board.getCells()[0][0].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[1][3].getStatus());

    ship = new Ship(ShipType.SUBMARINE);
    board.placeShip(ship, random);
    coords = new ArrayList<>();
    coords.add(new Coord(4, 6));
    coords.add(new Coord(5, 6));
    coords.add(new Coord(6, 6));

    for (int i = 0; i < ship.getPosition().size(); i++) {
      assertEquals(coords.get(i).getX(), ship.getPosition().get(i).getX());
      assertEquals(coords.get(i).getY(), ship.getPosition().get(i).getY());
    }
    board.placeShip(new Ship(ShipType.CARRIER), new Random(3));
    board.placeShip(new Ship(ShipType.BATTLESHIP), new Random(3));
    assertEquals(CellStatus.SHIP, board.getCells()[0][0].getStatus());
    assertEquals(CellStatus.SHIP, board.getCells()[5][6].getStatus());
  }

  /**
   * tests the getHitSpots method
   */
  @Test
  void getHitSpots() {
    List<Coord> spotsToHit = new ArrayList<>();
    spotsToHit.add(board.getCells()[0][1]);
    spotsToHit.add(board.getCells()[2][1]);
    spotsToHit.add(board.getCells()[1][0]);
    spotsToHit.add(board.getCells()[1][2]);
    board.getCells()[1][1].setStatus(CellStatus.HIT);
    List<Coord> actual = board.getHitSpots();
    for (int i = 0; i < actual.size(); i++) {
      assertEquals(spotsToHit.get(i).getX(), actual.get(i).getX());
      assertEquals(spotsToHit.get(i).getY(), actual.get(i).getY());
    }

    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        board.getCells()[i][j].setStatus(CellStatus.HIT);
      }
    }
    actual = board.getHitSpots();
    assertEquals(new ArrayList<>(), actual);
  }
}