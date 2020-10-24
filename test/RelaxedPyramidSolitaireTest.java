import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Represent tests for the RelaxedPyramidSolitaire model.
 */
public class RelaxedPyramidSolitaireTest {
  PyramidSolitaireModel<Card> relaxedModel;

  private void reset() {
    this.relaxedModel = new RelaxedPyramidSolitaire();
  }

  @Test
  public void testRemove() {
    reset();
    this.relaxedModel.startGame(this.relaxedModel.getDeck(), false, 7, 3);
    try {
      this.relaxedModel.remove(6, 1, 5, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("The pair is not a valid relaxed pair.", e.getMessage());
    }
    try {
      this.relaxedModel.remove(5, 0, 6, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("The pair is not a valid relaxed pair.", e.getMessage());
    }
    try {
      this.relaxedModel.remove(5, 2, 4, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("Both cards are not playable.", e.getMessage());
    }
    this.relaxedModel.removeUsingDraw(1, 6, 0);
    this.relaxedModel.remove(6, 1, 5, 0);
    assertNull(this.relaxedModel.getCardAt(6, 0));
    assertNull(this.relaxedModel.getCardAt(6, 1));
    assertNull(this.relaxedModel.getCardAt(5, 0));
    reset();
    this.relaxedModel.startGame(this.relaxedModel.getDeck(), false, 7, 3);
    this.relaxedModel.removeUsingDraw(1, 6, 0);
    this.relaxedModel.remove(5, 0, 6, 1);
    assertNull(this.relaxedModel.getCardAt(6, 0));
    assertNull(this.relaxedModel.getCardAt(6, 1));
    assertNull(this.relaxedModel.getCardAt(5, 0));
    reset();
  }

  @Test
  public void testIsGameOver() {
    reset();
    try {
      this.relaxedModel.isGameOver();
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.relaxedModel.startGame(this.relaxedModel.getDeck(), false, 7, 1);
    assertFalse(this.relaxedModel.isGameOver());
    this.relaxedModel.remove(6, 2, 6, 6);
    this.relaxedModel.remove(6, 3, 6, 5);
    this.relaxedModel.remove(6, 4);
    this.relaxedModel.remove(5, 2, 5, 5);
    this.relaxedModel.remove(5, 3, 5, 4);
    this.relaxedModel.remove(4, 2);
    for (int i = 0; i < 24; i++) {
      this.relaxedModel.discardDraw(0);
    }
    assertTrue(this.relaxedModel.isGameOver());

    reset();
    this.relaxedModel.startGame(this.relaxedModel.getDeck(), false, 7, 2);
    assertFalse(this.relaxedModel.isGameOver());
    this.relaxedModel.remove(6, 2, 6, 6);
    this.relaxedModel.remove(6, 3, 6, 5);
    this.relaxedModel.remove(6, 4);
    this.relaxedModel.remove(5, 2, 5, 5);
    this.relaxedModel.remove(5, 3, 5, 4);
    this.relaxedModel.remove(4, 2);
    this.relaxedModel.removeUsingDraw(1, 6, 0);
    for (int i = 0; i < 23; i++) {
      this.relaxedModel.discardDraw(0);
    }
    assertFalse(this.relaxedModel.isGameOver());
    this.relaxedModel.remove(6, 1, 5, 0);
    assertTrue(this.relaxedModel.isGameOver());
  }

}