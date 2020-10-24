package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

/**
 * Represent a controller for the pyramid solitaire game. This controller allows users to interact
 * with the model with the following commands:
 * q: quit
 * rm1: remove 1 card
 * rm2: remove 2 cards
 * rmwd: remove 1 card with a draw card
 * dd: discard a draw card
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private Readable rd;
  private Appendable ap;

  /**
   * Construct a pyramid solitaire textual controller.
   *
   * @param rd a readable representing user inputs.
   * @param ap an appendable representing outputs.
   * @throws IllegalArgumentException if either rd or ap is null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Both input and output cannot be null.");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }
    if (deck == null) {
      throw new IllegalArgumentException("The deck cannot be null.");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game cannot be started. " + e.getMessage());
    }
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);

    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot receive input or transmit output.");
    }
    this.appendMessage("\n");
    this.appendMessage("Score: " + model.getScore() + "\n");

    String cmd;
    String[] values = new String[4];
    Scanner scan = new Scanner(this.rd);
    while (!model.isGameOver()) {
      if (!scan.hasNext()) {
        return;
      }
      cmd = scan.next();
      switch (cmd) {
        case "q":
        case "Q":
          this.handleQuit(model, view);
          return;
        case "rm1":
          for (int i = 0; i < 2; i++) {
            try {
              values[i] = this.nextValidInput(scan);
            } catch (IllegalStateException e) {
              this.appendMessage("Invalid input value, try again.\n");
              break;
            }
          }
          if (this.checkQuit(model, view, values)) {
            return;
          }
          try {
            model.remove(Integer.parseInt(values[0]) - 1, Integer.parseInt(values[1]) - 1);
          } catch (IllegalArgumentException e) {
            this.appendMessage("Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "rm2":
          for (int i = 0; i < 4; i++) {
            try {
              values[i] = this.nextValidInput(scan);
            } catch (IllegalStateException e) {
              this.appendMessage("Invalid input value, try again.\n");
              break;
            }
          }
          if (this.checkQuit(model, view, values)) {
            return;
          }
          try {
            model.remove(Integer.parseInt(values[0]) - 1, Integer.parseInt(values[1]) - 1,
                    Integer.parseInt(values[2]) - 1, Integer.parseInt(values[3]) - 1);
          } catch (IllegalArgumentException e) {
            this.appendMessage("Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "rmwd":
          for (int i = 0; i < 3; i++) {
            try {
              values[i] = this.nextValidInput(scan);
            } catch (IllegalStateException e) {
              this.appendMessage("Invalid input value, try again.\n");
              break;
            }
          }
          if (this.checkQuit(model, view, values)) {
            return;
          }
          try {
            model.removeUsingDraw(Integer.parseInt(values[0]) - 1,
                    Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]) - 1);
          } catch (IllegalArgumentException e) {
            this.appendMessage("Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "dd":
          try {
            values[0] = this.nextValidInput(scan);
          } catch (IllegalStateException e) {
            this.appendMessage("Invalid input value, try again.\n");
            break;
          }
          if (this.checkQuit(model, view, values)) {
            return;
          }
          try {
            model.discardDraw(Integer.parseInt(values[0]) - 1);
          } catch (IllegalArgumentException e) {
            this.appendMessage("Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        default:
          this.appendMessage("Invalid command. Try again.\n");
          break;
      }

      try {
        view.render();
        this.appendMessage("\n");
      } catch (IOException e) {
        throw new IllegalStateException("Cannot receive input or transmit output.");
      }
    }
  }

  /**
   * Append the given string to the appendable field.
   *
   * @param message string to be appended.
   */
  private void appendMessage(String message) {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot receive input or transmit output.");
    }
  }

  /**
   * Handle the quitting situation, append the quitting message to the appendable.
   *
   * @param view  some view for the pyramid solitaire game.
   * @param model some model for the pyramid solitaire game.
   * @param <K>   the type of cards.
   */
  private <K> void handleQuit(PyramidSolitaireModel<K> model, PyramidSolitaireView view) {
    this.appendMessage("Game quit!\nState of the game when quit:\n");
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot receive input or transmit output.");
    }
    this.appendMessage("\n");
    this.appendMessage("Score: " + model.getScore() + "\n");
  }

  /**
   * Check input values to decide if the program should quit.
   *
   * @param model the model.
   * @param view the view.
   * @param values the input value string array.
   * @param <K> the type of cards.
   * @return true if the program should quit, and call handleQuit(), false otherwise.
   */
  private <K> boolean checkQuit (PyramidSolitaireModel<K> model, PyramidSolitaireView view,
                                 String[] values) {
    for (String s : values) {
      if (s == null) {
        break;
      }
      if (s.equalsIgnoreCase("q")) {
        this.handleQuit(model, view);
        return true;
      }
    }
    return false;
  }
  /**
   * Get the next valid input value, either an integer, a "q", or a "Q".
   *
   * @return a string of a valid input.
   * @throws IllegalStateException if next valid input does not exist.
   */

  private String nextValidInput(Scanner scan) throws IllegalStateException {
    String result = "";
    while (!result.equalsIgnoreCase("q") && !this.isInteger(result)) {
      if (scan.hasNext()) {
        result = scan.next();
      } else {
        throw new IllegalStateException("Next valid input does not exist.");
      }
    }
    return result;
  }

  /**
   * Check if the given string is an integer.
   *
   * @param s the given string.
   * @return true if the string is an integer, false otherwise.
   */
  private boolean isInteger(String s) {
    if (s.isEmpty()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      if (i == 0 && s.charAt(i) == '-') {
        if (s.length() == 1) {
          return false;
        } else {
          continue;
        }
      }
      if (Character.digit(s.charAt(i), 10) < 0) {
        return false;
      }
    }
    return true;
  }
}