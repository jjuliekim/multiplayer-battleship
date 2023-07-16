package me.julie.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for GameResult
 */
public class GameResultTest {
  private GameResult win;
  private GameResult lose;
  private GameResult draw;

  /**
   * Sets up GameResults for testing
   */
  @BeforeEach
  public void setup() {
    this.win = GameResult.WIN;
    this.lose = GameResult.LOSE;
    this.draw = GameResult.DRAW;
  }

  /**
   * Tests win
   */
  @Test
  public void testWin() {
    assertEquals(this.win.toString(), "WIN");
  }

  /**
   * Tests lose
   */
  @Test
  public void testLose() {
    assertEquals(this.lose.toString(), "LOSE");
  }

  /**
   * Tests draw
   */
  @Test
  public void testDraw() {
    assertEquals(this.draw.toString(), "DRAW");
  }
}

