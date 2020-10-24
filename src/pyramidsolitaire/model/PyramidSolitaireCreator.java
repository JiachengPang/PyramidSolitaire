package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represent a Creator class for the pyramid solitaire game, create an instance of pyramid solitaire
 * game based on the given input game type.
 */
public class PyramidSolitaireCreator {

  /**
   * Represent a type of a pyramid solitaire game.
   *  - BASIC - basic pyramid solitaire game.
   *  - RELAXED - relaxed pyramid solitaire game.
   *  - TRIPEAKS - tripeaks pyramid solitaire game.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID;
  }

  /**
   * Create an instance of PyramidSolitaireModel based on the given game type.
   *  - BASIC for BasicPyramidSolitaire.
   *  - RELAXED for RelaxedPyramidSolitaire.
   *  - MULTIPYRAMID for MultiPyramidSolitaire.
   * @param type desired game type.
   * @return the corresponding PyramidSolitaireModel.
   */
  public static PyramidSolitaireModel create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();

      case RELAXED:
        return new RelaxedPyramidSolitaire();

      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();

      default:
        throw new IllegalArgumentException("Invalid game type.");
    }
  }

}
