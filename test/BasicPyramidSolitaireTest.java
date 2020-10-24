import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests for the BasicPyramidSolitaire Class.
 */
public class BasicPyramidSolitaireTest {
  PyramidSolitaireModel game1;
  PyramidSolitaireModel game2;
  List<cs3500.pyramidsolitaire.model.hw02.Card> list1;

  void reset() {
    this.game1 = new BasicPyramidSolitaire();
    this.game2 = new BasicPyramidSolitaire();
    this.list1 = new ArrayList<cs3500.pyramidsolitaire.model.hw02.Card>();
  }

  @Test
  public void testRemove() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 5, 1);
    this.game1.remove(4, 1, 4, 3);
    assertNull(this.game1.getCardAt(4, 1));
    assertNull(this.game1.getCardAt(4, 3));
    reset();
  }

  @Test
  public void testRemove2() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    try {
      this.game1.remove(4, 5, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("At least one index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(0, 0, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("At least one of the cards is not playable.", e.getMessage());
    }
    try {
      this.game1.remove(2, 1, 2, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The sum of these 2 cards is not 13.", e.getMessage());
    }
    try {
      this.game1.remove(4, 5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The index is invalid.", e.getMessage());
    }
    try {
      this.game1.remove(0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The card is not playable.", e.getMessage());
    }
    try {
      this.game1.remove(2, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The value of this card is not 13.", e.getMessage());
    }

    this.game2.startGame(this.game2.getDeck(), false, 5, 1);
    this.game2.remove(4, 1, 4, 3);
    try {
      this.game2.remove(4, 1, 4, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("One or both cards do not exist.", e.getMessage());
    }
    try {
      this.game2.remove(4, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The card does not exist.", e.getMessage());
    }
    reset();
  }


  @Test
  public void testRemoveUsingDraw() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    this.game1.removeUsingDraw(0, 2, 2);
    assertNull(this.game1.getCardAt(2, 2));
    assert (this.game1.getDrawCards().size() == this.game1.getNumDraw());
    reset();
  }

  @Test
  public void testRemoveUsingDraw2() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    try {
      this.game1.removeUsingDraw(-1, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The draw index is invalid.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(0, 3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The pyramid index is invalid.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(0, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The card is not playable.", e.getMessage());
    }
    try {
      this.game1.removeUsingDraw(0, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The sum of these 2 cards is not 13.", e.getMessage());
    }
    this.game1.removeUsingDraw(0, 2, 2);
    try {
      this.game1.removeUsingDraw(0, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The card in the pyramid does not exist.", e.getMessage());
    }
    reset();
  }



  @Test
  public void testDiscardDraw() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    assertEquals(1, this.game1.getDrawCards().size());
    this.game1.discardDraw(0);
    assertEquals(1, this.game1.getDrawCards().size());
    reset();
  }

  @Test
  public void testIsGameOver() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 2, 1);
    assertFalse(this.game1.isGameOver());
    for (int i = 0; i < 6; i++) {
      this.game1.discardDraw(0);
      assertFalse(this.game1.isGameOver());
    }
    this.game1.removeUsingDraw(0, 1, 1);
    assertFalse(this.game1.isGameOver());
    this.game1.removeUsingDraw(0, 1, 0);
    assertFalse(this.game1.isGameOver());
    this.game1.removeUsingDraw(0, 0, 0);
    assertTrue(this.game1.isGameOver());
    reset();
  }

  @Test
  public void testIsGameOver2() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 2, 1);
    assertFalse(this.game1.isGameOver());
    for (int i = 0; i < 49; i++) {
      assertFalse(this.game1.isGameOver());
      this.game1.discardDraw(0);
    }
    assertTrue(this.game1.isGameOver());
    reset();
  }


  @Test
  public void testGetScore() {
    reset();
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    assertEquals(21, this.game1.getScore());
    reset();
  }
}