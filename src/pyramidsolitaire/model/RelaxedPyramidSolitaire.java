package cs3500.pyramidsolitaire.model.hw04;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Represent a relaxed pyramid solitaire game with a standard 52 card deck. If player tries to
 * remove a pair of cards where one card is partially covered by the other, the game treats both
 * cards as exposed. All other rules remain the same as the basic pyramid solitaire game.
 */
public class RelaxedPyramidSolitaire extends APyramidSolitaire {

  /**
   * Construct a relaxed pyramid solitaire game with a default deck of 52 cards.
   *
   * @param deck      the deck facing down.
   * @param pyramid   the cards in the pyramid.
   * @param drawCards the draw cards.
   */
  public RelaxedPyramidSolitaire(List<Card> deck, Card[][] pyramid, List<Card> drawCards) {
    super(deck, pyramid, drawCards);
  }

  /**
   * Construct a relaxed pyramid solitaire game with a default deck of 52 cards and all fields as
   * default.
   */
  public RelaxedPyramidSolitaire() {
    // Only construct an instance of a relaxed pyramid solitaire game.
    // To start the game, call startGame.
    super();
  }


  /**
   * Remove a pair of playable cards with a sum of 13. Allow the player to remove a pair of cards
   * where one card is partially covered by the other.
   *
   * @param row1  row number of the first card.
   * @param card1 column number of the first card.
   * @param row2  row number of the second card.
   * @param card2 column number of the second card.
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    try {
      super.remove(row1, card1, row2, card2);
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("At least one of the cards is not playable.")) {
        throw new IllegalArgumentException(e.getMessage());
      } else if (!super.isPlayable(row1, card1) && !super.isPlayable(row2, card2)) {
        throw new IllegalArgumentException("Both cards are not playable.");
      }

      if (row2 == row1 + 1) {
        if (!(card2 == card1 && this.pyramid[row2][card2 + 1] == null)
                && !(card2 == card1 + 1 && this.pyramid[row2][card2 - 1] == null)) {
          throw new IllegalArgumentException("The pair is not a valid relaxed pair.");
        } else if (this.is13(this.pyramid[row1][card1], this.pyramid[row2][card2])) {
          this.pyramid[row1][card1] = null;
          this.pyramid[row2][card2] = null;
        } else {
          throw new IllegalArgumentException("The sum of these 2 cards is not 13.");
        }
      } else if (row1 == row2 + 1) {
        if (!(card1 == card2 && this.pyramid[row1][card1 + 1] == null)
                && !(card1 == card2 + 1 && this.pyramid[row1][card1 - 1] == null)) {
          throw new IllegalArgumentException("The pair is not a valid relaxed pair.");
        } else if (this.is13(this.pyramid[row1][card1], this.pyramid[row2][card2])) {
          this.pyramid[row1][card1] = null;
          this.pyramid[row2][card2] = null;
        } else {
          throw new IllegalArgumentException("The sum of these 2 cards is not 13.");
        }
      } else {
        throw new IllegalArgumentException("The pair is not a valid relaxed pair");
      }
    }
  }

  /**
   * Check if the game is over.
   *
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    super.isGameStarted();
    return super.emptyPyramid() || (!this.moveLeft() && this.deck.size() == 0);
  }

  /**
   * Check if there is any possible move left, including possible moves allowed by the relaxed
   * rule.
   *
   * @return true if there is at least one possible move left.
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  protected boolean moveLeft() {
    return super.moveLeft() || this.relaxedMoveLeft();
  }

  /**
   * Check if there is any move allowed by the relaxed rule still left in the pyramid.
   *
   * @return true if there is at least one relaxed move, false otherwise.
   */
  private boolean relaxedMoveLeft() {
    for (int i = 1; i < this.pyramid.length - 1; i++) {
      for (int j = 0; j < this.pyramid[i].length; j++) {
        if (this.pyramid[i][j] != null && super.isPlayable(i, j)) {
          if ((j != 0
                  && this.pyramid[i - 1][j - 1] != null)
                  && this.pyramid[i][j - 1] == null
                  && this.pyramid[i][j].getValue() + this.pyramid[i - 1][j - 1].getValue() == 13) {
            return true;
          } else if (this.pyramid[i - 1][j] != null
                  && this.pyramid[i][j + 1] == null
                  && this.pyramid[i][j].getValue() + this.pyramid[i - 1][j].getValue() == 13) {
            return true;
          }
        }
      }
    }

    int lastRowIndex = this.pyramid.length - 1;
    for (int i = 0; i < this.pyramid[lastRowIndex].length; i++) {
      if (this.pyramid[lastRowIndex][i] != null) {
        if ((i != 0
                && this.pyramid[lastRowIndex - 1][i - 1] != null)
                && this.pyramid[lastRowIndex][i - 1] == null
                && this.pyramid[lastRowIndex][i].getValue()
                + this.pyramid[lastRowIndex - 1][i - 1].getValue() == 13) {
          return true;
        } else if (this.pyramid[lastRowIndex - 1][i] != null
                && this.pyramid[lastRowIndex][i + 1] == null
                && this.pyramid[lastRowIndex][i].getValue()
                + this.pyramid[lastRowIndex - 1][i].getValue() == 13) {
          return true;
        }
      }
    }
    return false;
  }
}
