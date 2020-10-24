package cs3500.pyramidsolitaire.model.hw02;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw04.APyramidSolitaire;

/**
 * Represent a basic pyramid solitaire game consisting of a standard deck of 52 cards. Players need
 * to eliminate 1 or 2 cards that sum up to 13 at a time. Players can also use draw cards to help
 * eliminate the pyramid, as well as discarding the draw cards for new ones. When the pyramid is
 * cleared or there is no possible moves left, the game terminates.
 */
public class BasicPyramidSolitaire extends APyramidSolitaire {


  // All previous contents have been moved to cs3500.pyramidsolitaire.model.hw04.APyramidSolitaire

  public BasicPyramidSolitaire(List<Card> deck, Card[][] pyramid, List<Card> drawCards) {
    super(deck, pyramid, drawCards);
  }

  public BasicPyramidSolitaire() {
    super();
  }
}
