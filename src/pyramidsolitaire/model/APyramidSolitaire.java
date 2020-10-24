package cs3500.pyramidsolitaire.model.hw04;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;

/**
 * Represent an abstract PyramidSolitaire game. Players can make moves to remove cards from the
 * pyramid. The game terminates if there is no card left in the pyramid or there is no possible move
 * left. The score of the game is the sum of the values of all remaining cards in the pyramid. The
 * goal is to obtain a score as low as possible. Concrete rules should be specified in subclasses.
 */
public abstract class APyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected final List<Card> defaultDeck = this.getDeck();
  protected List<Card> deck;
  protected Card[][] pyramid;
  protected List<Card> drawCards;

  /**
   * Construct an abstract pyramid solitaire game, with a default deck depending on the rules.
   *
   * @param deck      the deck facing down.
   * @param pyramid   the cards in the pyramid.
   * @param drawCards the draw cards.
   */
  public APyramidSolitaire(List<Card> deck, Card[][] pyramid, List<Card> drawCards) {
    this.deck = Objects.requireNonNull(deck);
    this.pyramid = Objects.requireNonNull(pyramid);
    this.drawCards = Objects.requireNonNull(drawCards);
  }

  /**
   * Construct an abstract pyramid solitaire game with a default deck specified by subclasses, and
   * all fields as default.
   */
  public APyramidSolitaire() {
    // Only construct an instance of an abstract pyramid solitaire game.
    // To start the game, call startGame.
  }

  /**
   * Get a standard 52 card deck.
   *
   * @return a List of 52 cards.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> result = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        result.add(new Card(value, suit));
      }
    }
    return result;
  }
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    if (deck == null || !this.checkDeck(deck)) {
      throw new IllegalArgumentException("The deck is invalid.");
    } else if (numRows * (1 + numRows) / 2 + numDraw > deck.size()) {
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
      this.shuffleDeck(deck);
    }

    this.pyramid = new Card[numRows][numRows];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j <= i; j++) {
        pyramid[i][j] = this.deck.get(0);
        this.deck.remove(0);
      }
    }

    this.drawCards = new ArrayList<Card>(numDraw);
    for (int i = 0; i < numDraw; i++) {
      this.drawCards.add(this.deck.get(0));
      this.deck.remove(0);
    }
  }


  /**
   * Check if the deck is complete and valid.
   *
   * @param deck the deck to be checked.
   * @return true if the deck contains the same cards as the default deck, false otherwise.
   */
  protected boolean checkDeck(List<Card> deck) {
    return deck.containsAll(this.defaultDeck)
            && this.defaultDeck.containsAll(deck)
            && this.defaultDeck.size() == deck.size();
  }


  /**
   * Shuffle the deck, randomly rearrange the deck.
   */
  protected void shuffleDeck(List<Card> deck) {
    List<Card> temp = new ArrayList<Card>(deck);
    int selected;
    for (int i = 0; i < deck.size(); i++) {
      selected = (int) (Math.random() * (deck.size() - i));
      this.deck.add(temp.get(selected));
      temp.remove(selected);
    }
  }

  /**
   * Check if the game has been started.
   *
   * @throws IllegalStateException if the game has not been started.
   */
  protected void isGameStarted() throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("The game has not been started.");
    }
  }


  /**
   * Check if the given index is valid. Any index outside of the pyramid is invalid.
   *
   * @param row  the row of the card.
   * @param card the column of the card.
   * @return true if the given index is valid.
   */
  protected boolean validIndex(int row, int card) {
    return (row < this.pyramid.length && card <= row && card >= 0);
  }

  /**
   * Check if the card corresponding to the given coordinate is exposed.
   *
   * @param row  the row of the card.
   * @param card the column of the card.
   * @return true if the given card is playable.
   */
  protected boolean isPlayable(int row, int card) {
    return (row == this.pyramid.length - 1)
            || (this.pyramid[row + 1][card] == null && this.pyramid[row + 1][card + 1] == null);
  }

  /**
   * Check if the sum of 2 cards is 13.
   *
   * @param card1 the first card.
   * @param card2 the second card.
   * @return true if the sum is 13.
   */
  protected boolean is13(Card card1, Card card2) {
    return (card1.getValue() + card2.getValue() == 13);
  }

  /**
   * Check if the card value is 13.
   *
   * @param card a card.
   * @return true if the value of this card is 13.
   */
  protected boolean is13(Card card) {
    return card.getValue() == 13;
  }


  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    this.isGameStarted();
    if (!this.validIndex(row1, card1) || !this.validIndex(row2, card2)) {
      throw new IllegalArgumentException("At least one index is invalid.");
    } else if (this.pyramid[row1][card1] == null || this.pyramid[row2][card2] == null) {
      throw new IllegalArgumentException("One or both cards do not exist.");
    } else if (!(this.isPlayable(row1, card1) && this.isPlayable(row2, card2))) {
      throw new IllegalArgumentException("At least one of the cards is not playable.");
    } else if (!is13(this.pyramid[row1][card1], this.pyramid[row2][card2])) {
      throw new IllegalArgumentException("The sum of these 2 cards is not 13.");
    } else {
      this.pyramid[row1][card1] = null;
      this.pyramid[row2][card2] = null;
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    this.isGameStarted();
    if (!this.validIndex(row, card)) {
      throw new IllegalArgumentException("The index is invalid.");
    } else if (this.pyramid[row][card] == null) {
      throw new IllegalArgumentException("The card does not exist.");
    } else if (!this.isPlayable(row, card)) {
      throw new IllegalArgumentException("The card is not playable.");
    } else if (!this.is13(pyramid[row][card])) {
      throw new IllegalArgumentException("The value of this card is not 13.");
    }
    this.pyramid[row][card] = null;
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    this.isGameStarted();
    if (!this.validIndex(row, card)) {
      throw new IllegalArgumentException("The pyramid index is invalid.");
    } else if (drawIndex >= this.drawCards.size() || drawIndex < 0) {
      throw new IllegalArgumentException("The draw index is invalid.");
    } else if (this.pyramid[row][card] == null) {
      throw new IllegalArgumentException("The card in the pyramid does not exist.");
    } else if (!this.isPlayable(row, card)) {
      throw new IllegalArgumentException("The card is not playable.");
    } else if (!this.is13(this.pyramid[row][card], this.drawCards.get(drawIndex))) {
      throw new IllegalArgumentException("The sum of these 2 cards is not 13.");
    } else {
      this.pyramid[row][card] = null;
      if (this.deck.size() == 0) {
        this.drawCards.remove(drawIndex);
      } else {
        this.drawCards.set(drawIndex, this.deck.get(0));
        this.deck.remove(0);
      }
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    this.isGameStarted();
    if (drawIndex >= this.drawCards.size() || drawIndex < 0) {
      throw new IllegalArgumentException("The draw index is invalid.");
    }
    if (this.deck.size() != 0) {
      this.drawCards.set(drawIndex, this.deck.get(0));
      this.deck.remove(0);
    } else {
      this.drawCards.remove(drawIndex);
    }
  }

  @Override
  public int getNumRows() {
    if (this.pyramid == null) {
      return -1;
    } else {
      return this.pyramid.length;
    }
  }

  @Override
  public int getNumDraw() {
    if (this.pyramid == null) {
      return -1;
    } else {
      return this.drawCards.size();
    }
  }

  @Override
  public int getRowWidth(int row) {
    this.isGameStarted();
    if (this.pyramid.length <= row) {
      throw new IllegalArgumentException("Row does not exist.");
    }
    return row + 1;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.isGameStarted();
    return (this.emptyPyramid() || (!this.moveLeft() && this.deck.size() == 0));
  }

  /**
   * Check if the pyramid is empty, that is, all cards in the pyramid are eliminated.
   *
   * @return true if the pyramid is empty.
   * @throws IllegalStateException if the game has not started yet.
   */
  protected boolean emptyPyramid() {
    this.isGameStarted();
    for (Card[] cards : this.pyramid) {
      for (Card card : cards) {
        if (card != null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check if there is any possible move left.
   *
   * @return true if there is at least one possible move left.
   * @throws IllegalStateException if the game has not started yet.
   */
  protected boolean moveLeft() {
    this.isGameStarted();
    ArrayList<Card> exposed = new ArrayList<Card>(this.exposedCards());
    for (int i = 0; i < exposed.size(); i++) {
      if (is13(exposed.get(i))) {
        return true;
      }
      for (int j = i; j < exposed.size(); j++) {
        if (is13(exposed.get(i), exposed.get(j))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get a list of exposed cards in the pyramid and all draw cards.
   *
   * @return a List of exposed {@link Card}s.
   */
  protected List<Card> exposedCards() {
    ArrayList<Card> exposedCards = new ArrayList<Card>();
    for (int i = 0; i < this.pyramid.length; i++) {
      for (int j = 0; j < this.pyramid[i].length; j++) {
        if (this.pyramid[i][j] != null) {
          if (this.isPlayable(i, j)) {
            exposedCards.add(this.pyramid[i][j]);
          }
        }
      }
    }

    exposedCards.addAll(this.drawCards);
    return exposedCards;
  }

  @Override
  public int getScore() throws IllegalStateException {
    this.isGameStarted();
    int result = 0;
    for (Card[] cards : this.pyramid) {
      for (Card card : cards) {
        if (card != null) {
          result += card.getValue();
        }
      }
    }
    return result;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    this.isGameStarted();
    if (!this.validIndex(row, card)) {
      throw new IllegalArgumentException("The index is invalid.");
    }
    return this.pyramid[row][card];
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    this.isGameStarted();
    return new ArrayList<Card>(this.drawCards);
  }
}
