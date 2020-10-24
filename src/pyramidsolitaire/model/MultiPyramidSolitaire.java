package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;

/**
 * Represent a TriPeak pyramid solitaire game. The game uses a double deck with 104 cards. the board
 * consists of 3 pyramids that overlap for half of their height (rounding up). ALl other rules
 * remain the same as the basic pyramid solitaire game.
 */
public class MultiPyramidSolitaire extends APyramidSolitaire {
  private List<Card> doubleDeck = this.getDeck();

  /**
   * Construct a TriPeak pyramid solitaire game with a default deck of 104 cards.
   *
   * @param deck      the deck facing down.
   * @param pyramid   the cards in the pyramid.
   * @param drawCards the draw cards.
   */
  public MultiPyramidSolitaire(List<Card> deck, Card[][] pyramid, List<Card> drawCards) {
    super(deck, pyramid, drawCards);
  }

  /**
   * Construct a TriPeak pyramid solitaire game with a default deck of 104 cards and all fields as
   * default.
   */
  public MultiPyramidSolitaire() {
    // Only construct an instance of an TriPeak pyramid solitaire game.
    // To start the game, call startGame.
    super();
  }


  @Override
  public List<Card> getDeck() {
    List<Card> result = new ArrayList<Card>();
    List<Card> temp = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (Value v : Value.values()) {
        temp.add(new Card(v, suit));
      }
    }
    result.addAll(temp);
    result.addAll(temp);
    return result;
  }

  /**
   * Check if the given deck is enough for the given numRows and numDraw.
   *
   * @param rows  the given number of rows.
   * @param draws the given number of draw cards.
   * @param deck  the given deck.
   * @return true if the deck is enough, false otherwise.
   */
  private boolean enoughCards(int rows, int draws, List<Card> deck) {
    return ((rows / 2 + 1) * (rows / 2) * 3 / 2
            + ((rows / 2 + 1) * 3 - 2) * Math.ceil(rows / 2.0)
            + Math.ceil(rows / 2.0) * ((Math.ceil(rows / 2.0) - 1) / 2))
            + draws <= deck.size();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    if (deck == null || !this.checkDeck(deck)) {
      throw new IllegalArgumentException("The deck is invalid.");
    } else if (!this.enoughCards(numRows, numDraw, deck)) {
      throw new IllegalArgumentException("Too many rows or too many draw cards.");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Need at least 1 row to start the game.");
    } else if (numDraw < 0) {
      throw new IllegalArgumentException("Number of draw cards cannot be less than 0.");
    }

    this.deck = new ArrayList<Card>();
    if (!shuffle) {
      this.deck.addAll(deck);
    } else {
      super.shuffleDeck(deck);
    }

    this.pyramid = new Card[numRows][((numRows / 2) + 1) * 3 - 2
            + (int) (Math.ceil(numRows / 2.0)) - 1];
    for (int i = 0; i < numRows / 2; i++) {
      int x = 0;
      for (int j = 0; j < 3; j++) {
        for (int y = 0; y < i + 1; y++) {
          this.pyramid[i][x] = this.deck.get(0);
          this.deck.remove(0);
          x++;
        }
        x += numRows / 2 - 1 - i;
      }
    }
    for (int i = 0; i < (int) (Math.ceil(numRows / 2.0)); i++) {
      for (int j = 0; j < ((numRows / 2) + 1) * 3 - 2 + i; j++) {
        this.pyramid[i + numRows / 2][j] = this.deck.get(0);
        this.deck.remove(0);
      }
    }

    this.drawCards = new ArrayList<Card>(numDraw);
    for (int i = 0; i < numDraw; i++) {
      this.drawCards.add(this.deck.get(0));
      this.deck.remove(0);
    }
  }

  @Override
  protected boolean checkDeck(List<Card> deck) {
    List<Card> temp = new ArrayList<>(deck);
    for (Card c : this.doubleDeck) {
      if (temp.contains(c)) {
        temp.remove(c);
      } else {
        return false;
      }
    }
    return deck.size() == this.doubleDeck.size();
  }

  @Override
  protected boolean validIndex(int row, int card) {
    return (row < this.pyramid.length && card < this.getRowWidth(row) && row >= 0 && card >= 0);
  }

  @Override
  public int getRowWidth(int row) {
    this.isGameStarted();
    if (row >= this.getNumRows()) {
      throw new IllegalArgumentException("Row does not exist.");
    }
    int firstRowWidth = (super.getNumRows() / 2) * 2 + 1;
    return firstRowWidth + row;
  }
}
