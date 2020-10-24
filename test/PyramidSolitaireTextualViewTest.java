
import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the PyramidSolitaireTextualView Class.
 */

public class PyramidSolitaireTextualViewTest {
  PyramidSolitaireModel game1;
  PyramidSolitaireModel game2;
  PyramidSolitaireModel game3;
  PyramidSolitaireTextualView view1;

  private void reset() {
    this.game1 = new BasicPyramidSolitaire();
    this.game2 = new RelaxedPyramidSolitaire();
    this.game3 = new MultiPyramidSolitaire();
  }

  @Test
  public void testToString() {
    reset();
    this.view1 = new PyramidSolitaireTextualView(game1);
    this.game1.startGame(this.game1.getDeck(), false, 3, 1);
    assertEquals("    A♥\n  2♥  3♥\n4♥  5♥  6♥\nDraw: 7♥", this.view1.toString());
    reset();
  }

  @Test
  public void testToString2() {
    reset();
    this.view1 = new PyramidSolitaireTextualView(game1);
    this.game1.startGame(this.game1.getDeck(), false, 7, 2);
    assertEquals("            A♥\n" +
            "          2♥  3♥\n" +
            "        4♥  5♥  6♥\n" +
            "      7♥  8♥  9♥  10♥\n" +
            "    J♥  Q♥  K♥  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
            "Draw: 3♠, 4♠", this.view1.toString());
    reset();
  }

  @Test
  public void testToString3() {
    reset();
    this.view1 = new PyramidSolitaireTextualView(game1);
    try {
      this.game1.startGame(this.game1.getDeck(), false, -1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("", this.view1.toString());
    }
    reset();
  }

  @Test
  public void testRelaxed() {
    reset();
    this.view1 = new PyramidSolitaireTextualView(game2);
    this.game2.startGame(this.game2.getDeck(), false, 7, 2);
    assertEquals("            A♥\n" +
            "          2♥  3♥\n" +
            "        4♥  5♥  6♥\n" +
            "      7♥  8♥  9♥  10♥\n" +
            "    J♥  Q♥  K♥  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
            "Draw: 3♠, 4♠", this.view1.toString());
    reset();
  }

  @Test
  public void testTripeaks() {
    reset();
    this.view1 = new PyramidSolitaireTextualView(game3);
    this.game3.startGame(this.game3.getDeck(), false, 7, 2);
    assertEquals("            A♥  .   .   2♥  .   .   3♥\n" +
            "          4♥  5♥  .   6♥  7♥  .   8♥  9♥\n" +
            "        10♥ J♥  Q♥  K♥  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
            "    3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠  K♠\n" +
            "  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "K♣  A♥  2♥  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥\n" +
            "Draw: K♥, A♦", this.view1.toString());
    reset();
  }


}