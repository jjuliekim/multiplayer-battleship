package me.julie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a board in a Battleship game
 */
public class Board {
  private final int height;
  private final int width;
  private final Coord[][] cells;

  /**
   * Initializes a new Board with the given height and width
   *
   * @param height height of the board
   * @param width width of the board
   */
  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    this.cells = initBoard();
  }

  /**
   * Returns this board's height
   *
   * @return the height of this board
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the width of this board
   *
   * @return width of the board
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the cells in this board
   *
   * @return 2d array of Coords representing the cells in this board
   */
  public Coord[][] getCells() {
    return cells;
  }

  /**
   * Sets the cell at the given coordinate's position to the given coord
   *
   * @param coord Coord to set the cell to
   */
  public void setCell(Coord coord) {
    cells[coord.getY()][coord.getX()] = coord;
  }

  /**
   * Initializes a new board to all water
   *
   * @return a new board containing all water
   */
  private Coord[][] initBoard() {
    Coord[][] board = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Coord(j, i, CellStatus.WATER);
      }
    }
    return board;
  }

  /**
   * Places a ship on this board
   *
   * @param ship Ship to place on this board
   * @param random Random seed
   */
  public void placeShip(Ship ship, Random random) {
    List<Coord> coords = new ArrayList<>();
    boolean vertical = random.nextBoolean();
    Coord cell = null;
    boolean space = false;
    while (!space) {
      cell = availableSpaces(ship, vertical, random);
      for (int i = 0; i < ship.getType().getLength(); i++) {
        if (vertical) {
          if (!(getCells()[cell.getY() + i][cell.getX()].getStatus().equals(CellStatus.WATER))) {
            space = false;
            break;
          } else {
            space = true;
          }
        } else {
          if (!(getCells()[cell.getY()][cell.getX() + i].getStatus().equals(CellStatus.WATER))) {
            space = false;
            break;
          } else {
            space = true;
          }
        }
      }
    }

    for (int i = 0; i < ship.getType().getLength(); i++) {
      if (vertical) {
        setCell(new Coord(cell.getX(), cell.getY() + i, CellStatus.SHIP));
        coords.add(getCells()[cell.getY() + i][cell.getX()]);
      } else {
        setCell(new Coord(cell.getX() + i, cell.getY(), CellStatus.SHIP));
        coords.add(getCells()[cell.getY()][cell.getX() + i]);
      }
    }
    ship.setPosition(coords);
  }

  /**
   * Lists the available spaces to place the given ship on this board
   *
   * @param ship Ship to place on this board
   * @param vertical direction of this Ship
   * @param random Random seed
   *
   * @return list of available Coords to place the given ship on this board
   */
  private Coord availableSpaces(Ship ship, boolean vertical, Random random) {
    Coord coord;
    int length = ship.getType().getLength();

    if (vertical) {
      if (height == length) {
        coord = getCells()[0][random.nextInt(width)];
      } else {
        coord = getCells()[random.nextInt(height - length)][random.nextInt(width)];
      }
    } else {
      if (width == length) {
        coord = getCells()[random.nextInt(height)][0];
      } else {
        coord = getCells()[random.nextInt(height)][random.nextInt(width - length)];
      }
    }
    return coord;
  }

  /**
   * Returns spots on opponent's board that surround a previous hit
   *
   * @return list of coords surrounding a previous hit
   */
  public ArrayList<Coord> getHitSpots() {
    ArrayList<Coord> spotsToHit = new ArrayList<>();

    for (int y = 0; y < height; y += 1) {
      for (int x = 0; x < width; x += 1) {
        if (getCells()[y][x].getStatus().equals(CellStatus.HIT)) {
          if (y > 0 && getCells()[y - 1][x].getStatus().equals(CellStatus.WATER)) {
            spotsToHit.add(new Coord(x, y - 1));
          }
          if (y < this.height - 1 && getCells()[y + 1][x].getStatus().equals(CellStatus.WATER)) {
            spotsToHit.add(new Coord(x, y + 1));
          }
          if (x > 0 && getCells()[y][x - 1].getStatus().equals(CellStatus.WATER)) {
            spotsToHit.add(new Coord(x - 1, y));
          }
          if (x < this.width - 1 && getCells()[y][x + 1].getStatus().equals(CellStatus.WATER)) {
            spotsToHit.add(new Coord(x + 1, y));
          }
        }
      }
    }

    return spotsToHit;
  }
}
