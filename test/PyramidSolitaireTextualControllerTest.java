import org.junit.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link cs3500.pyramidsolitaire.controller.PyramidSolitaireController}, using mock model
 * {@link ConfirmInputModel}.
 */
public class PyramidSolitaireTextualControllerTest {

  private StringBuilder log;
  private PyramidSolitaireModel mock;
  private PyramidSolitaireModel model;
  private PyramidSolitaireController controller;

  /**
   * Reset the log to be a new StringBuilder and reset {@link ConfirmInputModel} to contain that
   * log.
   */
  private void reset() {
    this.log = new StringBuilder();
    this.mock = new ConfirmInputModel(log);
    this.model = new BasicPyramidSolitaire();
  }


  /**
   * Create a new print interaction for program outputs.
   *
   * @param lines program outputs.
   * @return print Interaction.
   */
  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append("\n");
      }
    };
  }

  /**
   * Create a new print interaction for user inputs.
   *
   * @param in user inputs.
   * @return input Interaction.
   */
  static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in);
    };
  }

  /**
   *  To test the actual controller with the interaction interface.
   * @param model the model.
   * @param deck the deck.
   * @param shuffle if the deck needs to be shuffled.
   * @param numRows number of rows.
   * @param numDraw number of draw cards.
   * @param interactions the interactions
   */
  private void testPlayGame(PyramidSolitaireModel model, List deck, boolean shuffle,
                            int numRows, int numDraw, Interaction... interactions) {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    PyramidSolitaireController controller =
            new PyramidSolitaireTextualController(input, actualOutput);
    controller.playGame(model, deck, shuffle, numRows, numDraw);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testInputRemove1() {
    reset();
    Reader in = new StringReader("rm1 7 1");
    StringBuilder dontCare = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("remove1: row: 6 card: 0", log.toString());
    reset();
    in = new StringReader("rm1 0 100");
    controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    reset();
  }


  @Test
  public void testInputRemove2() {
    reset();
    Reader in = new StringReader("rm2 7 1 0 0");
    StringBuilder dontCare = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("remove2: row1: 6 card1: 0 row2: -1 card2: -1", log.toString());
    reset();
    in = new StringReader("rm2 4 1 4 4");
    controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("remove2: row1: 3 card1: 0 row2: 3 card2: 3", log.toString());
    reset();
  }

  @Test
  public void testInputRemoveUsingDraw() {
    reset();
    Reader in = new StringReader("rmwd 9 8 7");
    StringBuilder dontCare = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("removeUsingDraw: drawIndex: 8 row: 7 card: 6", log.toString());
    reset();
    in = new StringReader("rmwd 4 2 1");
    controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("removeUsingDraw: drawIndex: 3 row: 1 card: 0", log.toString());
    reset();
  }

  @Test
  public void testInputDiscardDraw() {
    reset();
    Reader in = new StringReader("dd 0");
    StringBuilder dontCare = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("discardDraw: drawIndex: -1", log.toString());
    reset();
    in = new StringReader("dd 100");
    controller = new PyramidSolitaireTextualController(in, dontCare);
    controller.playGame(mock, mock.getDeck(), true, 7, 2);
    assertEquals("discardDraw: drawIndex: 99", log.toString());
    reset();
  }

  @Test
  public void testControllerConstructor() {
    reset();
    try {
      this.controller = new PyramidSolitaireTextualController(null, System.out);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Both input and output cannot be null.", e.getMessage());
    }
    try {
      this.controller =
              new PyramidSolitaireTextualController(new InputStreamReader(System.in), null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Both input and output cannot be null.", e.getMessage());
    }
    try {
      this.controller = new PyramidSolitaireTextualController(null, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Both input and output cannot be null.", e.getMessage());
    }
    reset();
  }

  @Test
  public void testPlayGame2() {
    reset();
    this.controller = new PyramidSolitaireTextualController(new InputStreamReader(System.in),
            System.out);
    try {
      this.controller.playGame(null, this.model.getDeck(),
              false, 5, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The model cannot be null.", e.getMessage());
    }
    try {
      this.controller.playGame(this.model, this.model.getDeck(),
              true, 10, 2);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Game cannot be started. Too many rows or too many draw cards.",
              e.getMessage());
    }
    try {
      this.controller.playGame(this.model, this.model.getDeck(),
              true, -1, 2);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Game cannot be started. Need at least 1 row to start the game.",
              e.getMessage());
    }
    reset();
  }

  @Test
  public void testRemove_1() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("rm1 a b 4 c 1\n"),
            prints("Invalid move. Play again. The card is not playable.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm1 10 10\n"),
            prints("Invalid move. Play again. The index is invalid.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm1 5 3\n"),
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm1 d 5 ccc 1\n"),
            prints("Invalid move. Play again. The value of this card is not 13.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm1 5 3\n"),
            prints("Invalid move. Play again. The card does not exist.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("a\n"),
            prints("Invalid command. Try again.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm2 6 2 5 1\n"),
            prints("Invalid move. Play again. At least one index is invalid.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rmwd 3 5 1\n"),
            prints("Invalid move. Play again. The draw index is invalid.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("dd 3\n"),
            prints("Invalid move. Play again. The draw index is invalid.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"));
    assertFalse(this.model.isGameOver());
    reset();
  }

  @Test
  public void testRemove_2() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("rm2 a b c 5 1 b c 5 2\n"),
            prints("Invalid move. Play again. The sum of these 2 cards is not 13.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm2 2 10 3 1\n"),
            prints("Invalid move. Play again. At least one index is invalid.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm2 5 1 2 1\n"),
            prints("Invalid move. Play again. At least one of the cards is not playable.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm2 5 1 5 5\n"),
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    ".   Q♥  K♥  A♦  .\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm2 5 1 5 5\n"),
            prints("Invalid move. Play again. One or both cards do not exist.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    ".   Q♥  K♥  A♦  .\n" +
                    "Draw: 3♦, 4♦"));
    assertFalse(this.model.isGameOver());
    reset();
  }

  @Test
  public void testRemoveUsingDraw() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 6, 2,
            prints("          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    "3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, 10♦\n" +
                    "Score: 127"),
            inputs("rmwd 1 4 5\n"),
            prints("Invalid move. Play again. The pyramid index is invalid.\n" +
                    "          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    "3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, 10♦"),
            inputs("rmwd 3 4 1\n"),
            prints("Invalid move. Play again. The draw index is invalid.\n" +
                    "          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    "3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, 10♦"),
            inputs("rmwd 1 3 1\n"),
            prints("Invalid move. Play again. The card is not playable.\n" +
                    "          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    "3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, 10♦"),
            inputs("rmwd a 2 n 6 i 1\n"),
            prints("          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    ".   4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, J♦"),
            inputs("rmwd 2 6 1\n"),
            prints("Invalid move. Play again. The card in the pyramid does not exist.\n" +
                    "          A♥\n" +
                    "        2♥  3♥\n" +
                    "      4♥  5♥  6♥\n" +
                    "    7♥  8♥  9♥  10♥\n" +
                    "  J♥  Q♥  K♥  A♦  2♦\n" +
                    ".   4♦  5♦  6♦  7♦  8♦\n" +
                    "Draw: 9♦, J♦"));
    assertFalse(this.model.isGameOver());
    reset();
  }

  @Test
  public void testDiscardDraw() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 9, 5,
            prints("                A♥\n" +
                    "              2♥  3♥\n" +
                    "            4♥  5♥  6♥\n" +
                    "          7♥  8♥  9♥  10♥\n" +
                    "        J♥  Q♥  K♥  A♦  2♦\n" +
                    "      3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "    9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
                    "  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠\n" +
                    "J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣\n" +
                    "Draw: 7♣, 8♣, 9♣, 10♣, J♣\n" +
                    "Score: 294"),
            inputs("dd 6\n"),
            prints("Invalid move. Play again. The draw index is invalid.\n" +
                    "                A♥\n" +
                    "              2♥  3♥\n" +
                    "            4♥  5♥  6♥\n" +
                    "          7♥  8♥  9♥  10♥\n" +
                    "        J♥  Q♥  K♥  A♦  2♦\n" +
                    "      3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "    9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
                    "  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠\n" +
                    "J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣\n" +
                    "Draw: 7♣, 8♣, 9♣, 10♣, J♣"),
            inputs("dd ad 2\n"),
            prints("                A♥\n" +
                    "              2♥  3♥\n" +
                    "            4♥  5♥  6♥\n" +
                    "          7♥  8♥  9♥  10♥\n" +
                    "        J♥  Q♥  K♥  A♦  2♦\n" +
                    "      3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "    9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
                    "  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠\n" +
                    "J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣\n" +
                    "Draw: 7♣, Q♣, 9♣, 10♣, J♣"),
            inputs("dd 4\n"),
            prints("                A♥\n" +
                    "              2♥  3♥\n" +
                    "            4♥  5♥  6♥\n" +
                    "          7♥  8♥  9♥  10♥\n" +
                    "        J♥  Q♥  K♥  A♦  2♦\n" +
                    "      3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "    9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
                    "  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠\n" +
                    "J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣\n" +
                    "Draw: 7♣, Q♣, 9♣, K♣, J♣"),
            inputs("dd 1\n"),
            prints("                A♥\n" +
                    "              2♥  3♥\n" +
                    "            4♥  5♥  6♥\n" +
                    "          7♥  8♥  9♥  10♥\n" +
                    "        J♥  Q♥  K♥  A♦  2♦\n" +
                    "      3♦  4♦  5♦  6♦  7♦  8♦\n" +
                    "    9♦  10♦ J♦  Q♦  K♦  A♠  2♠\n" +
                    "  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠\n" +
                    "J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣\n" +
                    "Draw: Q♣, 9♣, K♣, J♣"));
    assertFalse(this.model.isGameOver());
    reset();
  }

  @Test
  public void testQuit() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("q\n"),
            prints("Game quit!\n" +
                    "State of the game when quit:\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"));
    assertFalse(this.model.isGameOver());
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("rm1 5 q\n"),
            prints("Game quit!\n" +
                    "State of the game when quit:\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"));
    assertFalse(this.model.isGameOver());
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("rm1 q 5\n"),
            prints("Game quit!\n" +
                    "State of the game when quit:\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"));
    assertFalse(this.model.isGameOver());
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("rm2 4 2 1 q\n"),
            prints("Game quit!\n" +
                    "State of the game when quit:\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"));
    assertFalse(this.model.isGameOver());
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("dd q\n"),
            prints("Game quit!\n" +
                    "State of the game when quit:\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"));
    assertFalse(this.model.isGameOver());
    reset();
  }

  @Test
  public void testCompleteGame() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 3,1,
            prints("    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  6♥\n" +
                    "Draw: 7♥\n" +
                    "Score: 21"),
            inputs("rmwd 1 3 3 rmwd 1 3 2 rmwd 1 3 1 rmwd 1 2 2 rmwd 1 2 1 rmwd 1 1 1\n"),
            prints("    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  .\n" +
                    "Draw: 8♥\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  .   .\n" +
                    "Draw: 9♥\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    ".   .   .\n" +
                    "Draw: 10♥\n" +
                    "    A♥\n" +
                    "  2♥  .\n" +
                    ".   .   .\n" +
                    "Draw: J♥\n" +
                    "    A♥\n" +
                    "  .   .\n" +
                    ".   .   .\n" +
                    "Draw: Q♥\n" +
                    "You win!"));
    assertTrue(this.model.isGameOver());
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 3, 1,
            prints("    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  6♥\n" +
                    "Draw: 7♥\n" +
                    "Score: 21"),
            inputs("rmwd a 1 3 3 rmwd 5 3 3 rmwd 1 4 3 rmwd 1 3 3 rmwd 1 3 2 " +
                    "rmwd 1 3 1 rmwd 1 2 2 rmwd 1 2 1 rmwd 1 1 1"),
            prints("    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  .\n" +
                    "Draw: 8♥\n" +
                    "Invalid move. Play again. The draw index is invalid.\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  .\n" +
                    "Draw: 8♥\n" +
                    "Invalid move. Play again. The pyramid index is invalid.\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  .\n" +
                    "Draw: 8♥\n" +
                    "Invalid move. Play again. The card in the pyramid does not exist.\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  5♥  .\n" +
                    "Draw: 8♥\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    "4♥  .   .\n" +
                    "Draw: 9♥\n" +
                    "    A♥\n" +
                    "  2♥  3♥\n" +
                    ".   .   .\n" +
                    "Draw: 10♥\n" +
                    "    A♥\n" +
                    "  2♥  .\n" +
                    ".   .   .\n" +
                    "Draw: J♥\n" +
                    "    A♥\n" +
                    "  .   .\n" +
                    ".   .   .\n" +
                    "Draw: Q♥\n" +
                    "You win!"));
    assertTrue(this.model.isGameOver());
    reset();
  }

  @Test
  public void testInvalidInputs() {
    reset();
    this.testPlayGame(this.model, this.model.getDeck(), false, 5, 2,
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Score: 94"),
            inputs("abcd\n"),
            prints("Invalid command. Try again.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  K♥  A♦  2♦\n" +
                    "Draw: 3♦, 4♦"),
            inputs("rm1 5 3 abcd\n"),
            prints("        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦\n" +
                    "Invalid command. Try again.\n" +
                    "        A♥\n" +
                    "      2♥  3♥\n" +
                    "    4♥  5♥  6♥\n" +
                    "  7♥  8♥  9♥  10♥\n" +
                    "J♥  Q♥  .   A♦  2♦\n" +
                    "Draw: 3♦, 4♦"));
    assertFalse(this.model.isGameOver());
    reset();
  }
}

