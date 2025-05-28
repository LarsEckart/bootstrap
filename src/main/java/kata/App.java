package kata;

import java.io.IOException;
import java.util.Scanner;

class App {

  public static void main(String[] args) throws IOException {
    var scanner = new Scanner(System.in);
    var game = new BlackjackGame();

    System.out.println("Welcome to Blackjack!");

    boolean playAgain = true;
    while (playAgain) {
      game.playRound(scanner);

      System.out.print("Play another round? (y/n): ");
      var response = scanner.nextLine().trim().toLowerCase();
      playAgain = response.equals("y") || response.equals("yes");
    }

    System.out.println("Thanks for playing!");
    scanner.close();
  }

  static String getActual() {
    return "4" + "2";
  }
}
