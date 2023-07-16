package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests the methods in the Bot class
 */
class BotTest {
  private Bot bot;
  private Board board;

  /**
   * initializes the bot and the board before each test
   */
  @BeforeEach
  void setupTests() {
    bot = new Bot();
    board = new Board(8, 8);
    bot.setBoard(board);
  }

  /**
   * tests the setBoard method in the Bot class
   */
  @Test
  void setBoard() {
    assertEquals(board, bot.getBoard());
    Board board2 = new Board(9, 7);
    bot.setBoard(board2);
    assertEquals(board2, bot.getBoard());
  }

  /**
   * tests the setOppBoard method in the Bot class
   */
  @Test
  void setOppBoard() {
    bot.setOppBoard(board);
    assertEquals(board, bot.getOpponentBoard());
    Board board2 = new Board(6, 8);
    bot.setOppBoard(board2);
    assertEquals(board2, bot.getOpponentBoard());
  }

  /**
   * tests the setShips method in the Bot class
   */
  @Test
  void setShips() {
    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.BATTLESHIP));
    ships.add(new Ship(ShipType.DESTROYER));
    bot.setShips(ships);
    assertEquals(ships, bot.getBotShips());
    assertEquals(2, bot.getBotShips().size());
  }

  /**
   * tests the name method in the Bot class
   */
  @Test
  void name() {
    assertEquals("AI", bot.name());
  }

  /**
   * tests the setup method in the Bot class
   */
  @Test
  void setup() {
    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.CARRIER));
    ships.add(new Ship(ShipType.BATTLESHIP));
    ships.add(new Ship(ShipType.DESTROYER));
    ships.add(new Ship(ShipType.SUBMARINE));

    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    List<Ship> actual = bot.setup(8, 8, map);
    for (int i = 0; i < actual.size(); i++) {
      assertEquals(ships.get(i).getType(), actual.get(i).getType());
    }
    assertEquals(4, actual.size());
  }

  /**
   * tests the takeShots method in the bot class
   */
  @Test
  void takeShots() {
    List<Coord> shots = new ArrayList<>();
    bot.setOppBoard(board);
    assertEquals(shots, bot.takeShots());

    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.CARRIER));
    ships.add(new Ship(ShipType.BATTLESHIP));
    ships.add(new Ship(ShipType.DESTROYER));
    bot.setShips(ships);
    bot.getOpponentBoard().setCell(new Coord(0, 0, CellStatus.HIT));
    assertEquals(3, bot.takeShots().size());
  }

  /**
   * tests the reportDamage method in the Bot class
   */
  @Test
  void reportDamage() {
    List<Coord> oppShots = new ArrayList<>();
    oppShots.add(new Coord(0, 0));
    oppShots.add(new Coord(1, 1));
    oppShots.add(new Coord(2, 2));
    oppShots.add(new Coord(3, 3));
    bot.reportDamage(oppShots);
    board.getCells()[0][0].setStatus(CellStatus.SHIP);
    board.getCells()[1][1].setStatus(CellStatus.WATER);
    board.getCells()[2][2].setStatus(CellStatus.SUNK);
    board.getCells()[3][3].setStatus(CellStatus.HIT);
    assertEquals(2, bot.reportDamage(oppShots).size());
  }

  /**
   * tests the successfulHits method in the Bot class
   */
  @Test
  void successfulHits() {
    List<Coord> hits = new ArrayList<>();
    hits.add(new Coord(0, 0));
    hits.add(new Coord(3, 2));
    hits.add(new Coord(1, 5));
    bot.setOppBoard(board);
    bot.successfulHits(hits);
    assertEquals(CellStatus.HIT, bot.getOpponentBoard().getCells()[0][0].getStatus());
    assertEquals(CellStatus.HIT, bot.getOpponentBoard().getCells()[2][3].getStatus());
    assertEquals(CellStatus.HIT, bot.getOpponentBoard().getCells()[5][1].getStatus());
    assertEquals(CellStatus.WATER, bot.getOpponentBoard().getCells()[2][2].getStatus());
  }
}