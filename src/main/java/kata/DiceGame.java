package kata;

import java.util.List;

class DiceGame {

  private final List<SixSidedDice> dice;

  public DiceGame(SixSidedDice dice) {
    this.dice = List.of(dice);
  }

  public DiceGame(List<SixSidedDice> dieces) {
    this.dice = dieces;
  }

  public int play() {
    GameResult result = new GameResult();
    for (SixSidedDice die : dice) {
      result.add(die.roll());
    }
    return result.asInt();
  }
}
