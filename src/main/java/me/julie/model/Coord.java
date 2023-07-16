package me.julie.model;

/**
 * Represents a Coord on a board
 */
public class Coord {
  public final int coordX;
  public final int coordY;
  private CellStatus status;

  /**
   * Initializes a new Coord with the given x and y coordinates
   *
   * @param x x-coordinate of this coord
   * @param y y-coordinate of this coord
   *
   */
  public Coord(int x, int y) {
    this.coordY = y;
    this.coordX = x;
  }

  /**
   * Initializes a new coord with the given x-coordinate, y-coordinate and CellStatus
   *
   * @param x      x-coordinate of this coord
   * @param y      y-coordinate of this coord
   * @param status status of this coord
   */
  public Coord(int x, int y, CellStatus status) {
    this.coordY = y;
    this.coordX = x;
    this.status = status;
  }

  /**
   * Returns the y-coordinate of this Coord
   *
   * @return y-coordinate
   */
  public int getY() {
    return coordY;
  }

  /**
   * Returns the x-coordinate of this Coord
   *
   * @return x-coordinate
   */
  public int getX() {
    return coordX;
  }

  /**
   * Returns the status of this coord
   *
   * @return the status of this coord
   */
  public CellStatus getStatus() {
    return status;
  }

  /**
   * Returns the status of this coord
   *
   * @param status status of this Coord
   */
  public void setStatus(CellStatus status) {
    this.status = status;
  }

  /**
   * Checks whether two Coords are equal based on their x and y positions
   *
   * @param other Coord to compare to
   * @return whether the two coords have the same x and y coordinates
   */
  public boolean equals(Coord other) {
    return this.coordX == other.getX() && this.coordY == other.getY();
  }
}
