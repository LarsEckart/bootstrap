package kata;

import java.util.List;

class DiceGame {

  private final List<Dice> dice;

  public DiceGame(Dice dice) {
    this.dice = List.of(dice);
  }

  public DiceGame(List<Dice> dieces) {
    this.dice = dieces;
  }

  public int play() {
    int sum = 0;
    for (Dice die : dice) {
      sum = die.roll();
    }
    return sum;
  }
}
