package kata.two;

import java.util.List;
import kata.Cup;
import kata.GameResult;
import kata.SixSidedDice;

public final class TwoDiceCup implements Cup {

  private final List<SixSidedDice> dieces;

  public TwoDiceCup(List<SixSidedDice> dice) {
    if (dice.size() != 2) {
      throw new IllegalArgumentException("Cup must have 2 dieces");
    }
    this.dieces = dice;
  }

  @Override
  public GameResult swing() {
    var result = new TwoDiceGameResult();
    for (var dice : dieces) {
      result.add(dice.roll());
    }
    return result;
  }

  @Override
  public String toString() {
    return "Cup[" + "dieces=" + dieces + ']';
  }
}
