package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.util.Scanner;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

/**
 * Represent an entry point for the pyramid solitaire game with a main method.
 */
public final class PyramidSolitaire {

  /**
   * Start a pyramid solitaire game, player needs to input a game type, and 2 optional numeric input
   * representing the number of rows and the number of draw cards in the game. The default row size
   * is 7 and the default number of draw cards is 3. Any invalid non-integer inputs after the first
   * command will start a default game of the input game type.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    PyramidSolitaireModel model = null;
    PyramidSolitaireCreator creator = new PyramidSolitaireCreator();
    Scanner scan = new Scanner(System.in);
    String input = "";
    String cmd = "";
    int rows = 7;
    int draws = 3;

    System.out.println("Enter the game type and optional number of rows and number of draw cards.");
    while (!cmd.equalsIgnoreCase("basic") && !cmd.equalsIgnoreCase("relaxed")
            && !cmd.equalsIgnoreCase("multipyramid") && scan.hasNextLine()) {
      input = scan.nextLine();
      if (input.indexOf(" ") != -1) {
        cmd = input.substring(0, input.indexOf(" "));
        input = input.substring(input.indexOf(" "));
      } else {
        cmd = input;
        input = "";
      }
      switch (cmd.toUpperCase()) {
        case "BASIC":
          model = creator.create(PyramidSolitaireCreator.GameType.BASIC);
          break;
        case "RELAXED":
          model = creator.create(PyramidSolitaireCreator.GameType.RELAXED);
          break;
        case "MULTIPYRAMID":
          model = creator.create(PyramidSolitaireCreator.GameType.MULTIPYRAMID);
          break;
        default:
          System.out.println("Invalid command, incorrect game type.");
          break;
      }
    }

    try {
      if (!input.trim().isEmpty() && input.trim().indexOf(" ") == -1) {
        System.out.println("Invalid values for the number of rows or number of draw cards. " +
                "Initiating the default game.");
      } else if (!input.trim().isEmpty() && input.trim().indexOf(" ") != -1) {
        rows = Integer.parseInt(input.trim().substring(0, input.trim().indexOf(" ")));
        input = input.trim().substring(input.trim().indexOf(" "));
        if (!input.trim().isEmpty() && input.trim().indexOf(" ") == -1) {
          draws = Integer.parseInt(input.trim());
        } else {
          draws = Integer.parseInt(input.trim().substring(0, input.trim().indexOf(" ")));
        }
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid values for the number of rows or number of draw cards. " +
              "Initiating the default game.");
    }

    try {
      new PyramidSolitaireTextualController(
              new InputStreamReader(System.in), System.out)
              .playGame(model, model.getDeck(), true, rows, draws);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

