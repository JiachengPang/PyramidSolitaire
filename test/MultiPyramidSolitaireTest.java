import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Represent tests for the MultiPyramidSolitaire model.
 */
public class MultiPyramidSolitaireTest {
  PyramidSolitaireModel multiModel;

  private void reset() {
    this.multiModel = new MultiPyramidSolitaire();
  }

  @Test
  public void testGetDeck() {
    reset();
    List<Card> defaultDeck = new ArrayList<Card>();
    List<Card> temp = new ArrayList<Card>();
    for (Value v : Value.values()) {
      for (Suit suit : Suit.values()) {
        temp.add(new Card(v, suit));
      }
    }
    defaultDeck.addAll(temp);
    defaultDeck.addAll(temp);
    assertTrue(this.multiModel.getDeck().containsAll(defaultDeck));
    assertTrue(defaultDeck.containsAll(this.multiModel.getDeck()));
    assertEquals(this.multiModel.getDeck().size(), defaultDeck.size());
    reset();
  }

  @Test
  public void testStartGame() {
    reset();
    List<Card> temp = new ArrayList<Card>();
    for (Value v : Value.values()) {
      for (Suit suit : Suit.values()) {
        temp.add(new Card(v, suit));
      }
    }
    try {
      this.multiModel.startGame(temp, false, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid.", e.getMessage());
    }
    try {
      this.multiModel.startGame(this.multiModel.getDeck(), false, 9, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Too many rows or too many draw cards.", e.getMessage());
    }
    try {
      this.multiModel.startGame(this.multiModel.getDeck(), false, 5, 80);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Too many rows or too many draw cards.", e.getMessage());
    }

    this.multiModel.startGame(this.multiModel.getDeck(), false, 4, 2);
    assertEquals(2, this.multiModel.getNumDraw());
    assertEquals(4, this.multiModel.getNumRows());
    assertEquals(157, this.multiModel.getScore());
    assertFalse(this.multiModel.isGameOver());
    reset();
  }


  @Test
  public void testGetRowWidth() {
    reset();
    try {
      this.multiModel.getRowWidth(0);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("The game has not been started.", e.getMessage());
    }
    this.multiModel.startGame(this.multiModel.getDeck(), false, 7, 3);
    try {
      this.multiModel.getRowWidth(7);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Row does not exist.", e.getMessage());
    }
    assertEquals(7, this.multiModel.getRowWidth(0));
    assertEquals(8, this.multiModel.getRowWidth(1));
    assertEquals(9, this.multiModel.getRowWidth(2));
    assertEquals(10, this.multiModel.getRowWidth(3));
    assertEquals(11, this.multiModel.getRowWidth(4));
    assertEquals(12, this.multiModel.getRowWidth(5));
    assertEquals(13, this.multiModel.getRowWidth(6));
    reset();
  }

  @Test
  public void testOverlap() {
    reset();
    this.multiModel.startGame(this.multiModel.getDeck(), false, 7, 1);
    for (int i = 0; i < this.multiModel.getNumRows(); i++) {
      for (int j = 0; j < this.multiModel.getRowWidth(i); j++) {
        if (i == 0 && j % 3 != 0) {
          assertNull(this.multiModel.getCardAt(i, j));
        } else if (i == 1 && (j == 2 || j == 5)) {
          assertNull(this.multiModel.getCardAt(i, j));
        } else {
          assertNotNull(this.multiModel.getCardAt(i, j));
        }
      }
    }
    reset();
  }

  @Test
  public void testOverlap2() {
    reset();
    this.multiModel.startGame(this.multiModel.getDeck(), false, 8, 1);
    for (int i = 0; i < this.multiModel.getNumRows(); i++) {
      for (int j = 0; j < this.multiModel.getRowWidth(i); j++) {
        if (i == 0 && j % 4 != 0) {
          assertNull(this.multiModel.getCardAt(i, j));
        } else if (i == 1 && (j == 2 || j == 3 || j == 6 || j == 7)) {
          assertNull(this.multiModel.getCardAt(i, j));
        } else if (i == 2 && (j == 3 || j == 7)) {
          assertNull(this.multiModel.getCardAt(i, j));
        } else {
          assertNotNull(this.multiModel.getCardAt(i, j));
        }
      }
    }
    reset();
  }

  @Test
  public void testRemove() {
    reset();
    this.multiModel.startGame(this.multiModel.getDeck(),
            false, 3, 1);
    this.multiModel.discardDraw(0);
    this.multiModel.discardDraw(0);
    this.multiModel.discardDraw(0);
    this.multiModel.removeUsingDraw(0, 2, 2);
    try {
      this.multiModel.remove(2, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("At least one of the cards is not playable.", e.getMessage());
    }
    reset();
  }

  @Test
  public void testIsGameOver() {
    reset();
    this.multiModel.startGame(this.multiModel.getDeck(), false, 3, 1);
    assertFalse(this.multiModel.isGameOver());
    this.multiModel.discardDraw(0);
    for (int i = this.multiModel.getNumRows() - 1; i >= 0 ; i--) {
      for (int j = this.multiModel.getRowWidth(i) - 1; j >= 0; j--) {
        assertFalse(this.multiModel.isGameOver());
        this.multiModel.removeUsingDraw(0, i, j);
      }
    }
    assertTrue(this.multiModel.isGameOver());
    reset();
  }

  @Test
  public void testDeck() {
    reset();
    List<Card> deck = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        deck.add(new Card(value, suit));
        deck.add(new Card(value, suit));
      }
    }
    this.multiModel.startGame(deck, false, 3, 1);
    deck.remove(103);
    deck.add(deck.get(101));
    try {
      this.multiModel.startGame(deck, false, 3, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid.", e.getMessage());
    }
    reset();
  }
}