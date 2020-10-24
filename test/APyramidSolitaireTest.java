import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Represent tests for the APyramidSolitaire class. This test class only tests common behaviors
 * among all three game types.
 */
public abstract class APyramidSolitaireTest {
  PyramidSolitaireModel<Card> game1;
  PyramidSolitaireModel<Card> game2;
  List<Card> list1;

  protected abstract PyramidSolitaireModel<Card> factory();

  void reset() {
    this.game1 = factory();
    this.game2 = factory();
    this.list1 = new ArrayList<Card>();
  }

  @Test
  public void testStartGame() {
    reset();
    assertEquals(-1, this.game1.getNumDraw());
    assertEquals(-1, this.game1.getNumRows());
    try {
      this.game1.isGameOver();
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    assertEquals(3, this.game1.getNumRows());
    assertEquals(1, this.game1.getNumDraw());
    assertTrue(this.game1.getScore() > 0);
    reset();
  }

  @Test
  public void testStartGame2() {
    reset();
    try {
      this.game1.startGame(this.list1, true, 5, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid.", e.getMessage());
    }
    try {
      this.game1.startGame(this.game1.getDeck(), true, 10, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Too many rows or too many draw cards.", e.getMessage());
    }
    try {
      this.game1.startGame(this.game1.getDeck(), true, 9, 8);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Too many rows or too many draw cards.", e.getMessage());
    }
    try {
      this.game1.startGame(this.game1.getDeck(), true, 0, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Need at least 1 row to start the game.", e.getMessage());
    }
    try {
      this.game1.startGame(this.game1.getDeck(), true, 2, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Number of draw cards cannot be less than 0.", e.getMessage());
    }
    reset();
  }

  @Test
  public void testStartGame3() {
    reset();
    this.game1.startGame(this.game1.getDeck(), true, 4, 2);
    this.game2.startGame(this.game2.getDeck(), false, 4, 2);
    boolean flag = false;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j <= i; j++) {
        if (!this.game1.getCardAt(i, j).equals(this.game2.getCardAt(i, j))) {
          flag = true;
        }
      }
    }
    assertTrue(flag);
    reset();
  }

  @Test
  public void testStartGame4() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 2, 1);
    assertFalse(this.game1.isGameOver());
    assertEquals(1, this.game1.getNumDraw());
    assertEquals(2, this.game1.getNumRows());
    this.game1.startGame(this.game1.getDeck(), false, 3, 2);
    assertEquals(2, this.game1.getNumDraw());
    assertEquals(3, this.game1.getNumRows());
    reset();
  }

  @Test
  public final void testRemove1() {
    reset();
    try {
      this.game1.remove(1, 1);
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.game1.startGame(this.game1.getDeck(), false, 5, 3);
    try {
      this.game1.remove(5, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(4, 9);
    } catch (IllegalArgumentException e) {
      assertEquals("The index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(3, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("The card is not playable.", e.getMessage());
    }
    try {
      this.game1.remove(4, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("The value of this card is not 13.", e.getMessage());
    }
    reset();
  }

  @Test
  public final void testRemove2() {
    reset();
    try {
      this.game1.remove(1, 1, 2, 1);
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.game1.startGame(this.game1.getDeck(), false, 5, 3);
    try {
      this.game1.remove(5, 1, 5, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("At least one index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(4, 1, 5, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("At least one index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(4, 9, 4, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("At least one index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(4, 0, 4, 9);
    } catch (IllegalArgumentException e) {
      assertEquals("At least one index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(4, 0, 4, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The sum of these 2 cards is not 13.", e.getMessage());
    }
    reset();
  }

  @Test
  public final void testRemoveUsingDraw() {
    reset();
    try {
      this.game1.removeUsingDraw(1, 1, 1);
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.game1.startGame(this.game1.getDeck(), false, 3, 3);
    try {
      this.game1.removeUsingDraw(1, 3, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The pyramid index is invalid.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(1, 3, 10);
    } catch (IllegalArgumentException e) {
      assertEquals("The pyramid index is invalid.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(3, 2, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The draw index is invalid.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(0, 1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The card is not playable.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(0, 2, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The sum of these 2 cards is not 13.", e.getMessage());
    }
    reset();
  }

  @Test
  public final void testDiscardDraw() {
    reset();
    try {
      this.game1.discardDraw(0);
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.game1.startGame(this.game1.getDeck(), false, 3, 3);
    try {
      this.game1.discardDraw(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("The draw index is invalid.", e.getMessage());
    }
    try {
      this.game1.discardDraw(3);
    } catch (IllegalArgumentException e) {
      assertEquals("The draw index is invalid.", e.getMessage());
    }
    reset();
  }

  @Test
  public final void testGetNumRows() {
    reset();
    assertEquals(-1, this.game1.getNumRows());
    this.game1.startGame(this.game1.getDeck(), false, 3, 3);
    assertEquals(3, this.game1.getNumRows());
    reset();
  }

  @Test
  public final void testGetNumDraw() {
    reset();
    assertEquals(-1, this.game1.getNumDraw());
    this.game1.startGame(this.game1.getDeck(), false, 3, 3);
    assertEquals(3, this.game1.getNumDraw());
    reset();
  }

  @Test
  public final void testGetDrawCards() {
    reset();
    try {
      this.game1.getDrawCards();
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    reset();
  }

  /**
   * Factory for BasicPyramidSolitaire.
   */
  public static final class TestBasic extends APyramidSolitaireTest {
    protected PyramidSolitaireModel<Card> factory() {
      return new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.BASIC);
    }
  }

  /**
   * Factory for RelaxedPyramidSolitaire.
   */
  public static final class TestRelaxed extends APyramidSolitaireTest {
    protected PyramidSolitaireModel<Card> factory() {
      return new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.RELAXED);
    }
  }

  /**
   * Factory for MultiPyramidSolitaire.
   */
  public static final class TestMulti extends APyramidSolitaireTest {
    protected PyramidSolitaireModel<Card> factory() {
      return new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.MULTIPYRAMID);
    }
  }


}