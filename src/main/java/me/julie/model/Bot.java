package me.julie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a Bot player in a game of Battleship
 */
public class Bot implements Player {
  private Board board;
  private Board opponentBoard;
  private List<Ship> botShips = new ArrayList<>();

  /**
   * Sets the bot player's board to the given board
   *
   * @param board Board to set this bot's board to
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  /**
   * Gets this bot's board
   *
   * @return this bot's board
   */
  public Board getBoard() {
    return this.board;
  }

  /**
   * Sets this bot's opponent's board to the given board
   *
   * @param board board to set this bot's opponent's board to
   */
  public void setOppBoard(Board board) {
    this.opponentBoard = board;
  }

  /**
   * Gets the opponent's board
   *
   * @return the opponent's board
   */
  public Board getOpponentBoard() {
    return opponentBoard;
  }

  /**
   * Sets this bot's ships to the given list of ships
   *
   * @param ships Ships to set this bot's ships to
   */
  public void setShips(List<Ship> ships) {
    this.botShips = ships;
  }

  /**
   * Gets this bot's list of ships
   *
   * @return list of ships that this bot has
   */
  public List<Ship> getBotShips() {
    return botShips;
  }

  /**
   * Returns the name of this bot
   *
   * @return the name of this bot
   */
  public String name() {
    return "AI";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    setBoard(new Board(height, width));
    setOppBoard(new Board(height, width));
    List<Ship> ships = new ArrayList<>();
    for (ShipType shipType : specifications.keySet()) {
      for (int i = 0; i < specifications.get(shipType); i++) {
        Ship ship = new Ship(shipType);
        board.placeShip(ship, new Random());
        ships.add(ship);
      }
    }
    setShips(ships);
    return ships;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    if (botShips.size() == 0) {
      return shots;
    }

    for (int i = botShips.size() - 1; i >= 0; i -= 1) {
      Ship ship = botShips.get(i);
      if (ship.shipSunk()) {
        botShips.remove(i);
      }
    }

    int shotsToTake = botShips.size();
    ArrayList<Coord> spacesAroundHits = this.opponentBoard.getHitSpots();
    Random rand = new Random();
    while (shotsToTake > 0) {
      if (spacesAroundHits.size() > 0) {
        Coord c = spacesAroundHits.remove(0);
        if (opponentBoard.getCells()[c.getY()][c.getX()].getStatus().equals(CellStatus.WATER)) {
          this.opponentBoard.setCell(new Coord(c.getX(), c.getY(), CellStatus.MISS));
          shots.add(c);
          shotsToTake -= 1;
        }
      } else {
        int x = rand.nextInt(this.opponentBoard.getWidth());
        int y = rand.nextInt(this.opponentBoard.getHeight());
        if (this.opponentBoard.getCells()[y][x].getStatus().equals(CellStatus.WATER)) {
          this.opponentBoard.setCell(new Coord(x, y, CellStatus.MISS));
          shots.add(new Coord(x, y));
          shotsToTake -= 1;
        }
      }
    }

    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsOnBoard = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      Coord cell = board.getCells()[shot.getY()][shot.getX()];
      if (cell.getStatus().equals(CellStatus.SHIP) || cell.getStatus().equals(CellStatus.HIT)) {
        cell.setStatus(CellStatus.HIT);
        shotsOnBoard.add(shot);
      } else if (cell.getStatus().equals(CellStatus.SUNK)) {
        continue;
      } else {
        cell.setStatus(CellStatus.MISS);
      }
    }
    return shotsOnBoard;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      this.opponentBoard.setCell(new Coord(c.getX(), c.getY(), CellStatus.HIT));
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    return;
  }
}
