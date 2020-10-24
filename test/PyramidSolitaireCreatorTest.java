import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;

import static org.junit.Assert.assertTrue;

/**
 * Represent tests for the PyramidSolitaireCreator Class.
 */
public class PyramidSolitaireCreatorTest {
  PyramidSolitaireCreator creator = new PyramidSolitaireCreator();

  @Test
  public void create() {
    assertTrue(creator.create(PyramidSolitaireCreator.GameType.BASIC)
            instanceof BasicPyramidSolitaire);
    assertTrue(creator.create(PyramidSolitaireCreator.GameType.RELAXED)
            instanceof RelaxedPyramidSolitaire);
    assertTrue(creator.create(PyramidSolitaireCreator.GameType.MULTIPYRAMID)
            instanceof MultiPyramidSolitaire);

  }
}