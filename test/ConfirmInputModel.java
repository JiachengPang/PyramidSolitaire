import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;

/**
 * Represent a mock model for input testing for the pyramid solitaire game controller.
 */
public class ConfirmInputModel implements PyramidSolitaireModel {
  private final StringBuilder log;

  public ConfirmInputModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public List<Card> getDeck() {
    return new ArrayList<>();
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw) {
    return;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    this.log.append("remove2: row1: " + row1 + " card1: " + card1
            + " row2: " + row2 + " card2: " + card2);
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    this.log.append("remove1: row: " + row + " card: " + card);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    this.log.append("removeUsingDraw: drawIndex: " + drawIndex + " row: " + row + " card: " + card);
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    this.log.append("discardDraw: drawIndex: " + drawIndex);
  }

  @Override
  public int getNumRows() {
    return -1;
  }

  @Override
  public int getNumDraw() {
    return 100;
  }

  @Override
  public int getRowWidth(int row) {
    return 100;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 100;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return new Card(Value.ONE, Suit.HEART);
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    return new ArrayList<>();
  }
}
