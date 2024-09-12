package kata;

import java.io.IOException;
import java.util.List;

class App {

  public static void main(String[] args) throws IOException {
    final SixSidedDice sixSidedDice = new SixSidedDice();
    var game =
        new DiceGame(
            new FourDiceCup(List.of(sixSidedDice, sixSidedDice, sixSidedDice, sixSidedDice)));
    var result = game.play();
    System.out.println(result.hasWon() ? "You win!" : "You lose!");

    var twoDicegame = new DiceGame(new TwoDiceCup(List.of(sixSidedDice, sixSidedDice)));
    result = twoDicegame.play();
    System.out.println(result.hasWon() ? "You win!" : "You lose!");
  }
}
