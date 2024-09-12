package kata;

import java.util.List;

public final class TwoDiceCup implements Cup {

  private final List<SixSidedDice> dieces;

  public TwoDiceCup(List<SixSidedDice> dice) {
    if (dice.size() != 2) {
      throw new IllegalArgumentException("Cup must have 2 dieces");
    }
    this.dieces = dice;
  }

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
