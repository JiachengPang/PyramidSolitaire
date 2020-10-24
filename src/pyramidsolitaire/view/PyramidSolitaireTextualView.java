package cs3500.pyramidsolitaire.view;


import java.io.IOException;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represent the view of the pyramid solitaire game.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable outputs;

  /**
   * Construct a pyramid solitaire textual view.
   *
   * @param model the model of the pyramid solitaire game.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.outputs = System.out;
  }

  /**
   * Construct a pyramid solitaire textual view with an appendable output.
   *
   * @param model   the model of the pyramid solitaire game.
   * @param outputs the textual output.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable outputs) {
    this.model = model;
    this.outputs = outputs;
  }

  @Override
  public String toString() {
    String result = "";
    if (this.model.getNumRows() == -1) {
      result = "";
    } else if (this.model.getScore() == 0) {
      result = "You win!";
    } else if (this.model.isGameOver() && this.model.getScore() != 0) {
      result = "Game over. Score: "
              + String.format(Integer.toString(this.model.getScore()), "%02d");
    } else {
      for (int i = 0; i < this.model.getNumRows(); i++) {
        if (this.rowString(i).trim().equals("")) {
          result += "\n";
        } else {
          result += this.rowString(i) + "\n";
        }
      }
      if (this.drawString().equals("")) {
        result += "Draw:";
      } else {
        result += "Draw: " + this.drawString();
      }
    }
    return result;
  }

  /**
   * Get a string representing the given row.
   *
   * @param row index of the row.
   * @return a string representing this row. "" if there is no card in the given row.
   */
  String rowString(int row) {
    String leadingSpaces = "";
    String result = "";
    for (int i = 0; i < this.model.getNumRows() - (row + 1); i++) {
      leadingSpaces += " ";
      leadingSpaces += " ";
    }

    for (int j = 0; j < this.model.getRowWidth(row); j++) {
      if (this.model.getCardAt(row, j) == null) {
        result += ".  ";
      } else {
        result += String.format("%-3s", this.model.getCardAt(row, j).toString());
      }
      result += " ";
    }
    result = leadingSpaces + result.replaceFirst("\\s++$", "");
    return result;
  }

  /**
   * Get a string representing the current draw cards.
   *
   * @return a string representing the draw cards, "" if there is no draw card.
   */
  String drawString() {
    String result = "";
    if (this.model.getDrawCards().size() == 0) {
      return result;
    }
    for (int i = 0; i < this.model.getDrawCards().size(); i++) {
      if (i != 0) {
        result += ", ";
      }
      result += this.model.getDrawCards().get(i).toString().trim();
    }
    return result;
  }

  @Override
  public void render() throws IOException {
    this.outputs.append(this.toString());
  }
}