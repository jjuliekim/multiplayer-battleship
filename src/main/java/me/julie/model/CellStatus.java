package me.julie.model;

/**
 * Represents the status of a cell
 */
public enum CellStatus {
  WATER("-"),
  SHIP("X"),
  HIT("="),
  MISS("O"),
  SUNK("#");

  private final String status;

  /**
   * Initializes a new CellStatus with the given status
   *
   * @param status status of this CellStatus
   */
  CellStatus(String status) {
    this.status = status;
  }

  /**
   * Returns a String representation of this CellStatus
   *
   * @return String representing this CellStatus
   */
  @Override
  public String toString() {
    return status;
  }
}
