package kata;

import java.util.List;

public final class FourDiceCup implements Cup {

  private final List<SixSidedDice> dieces;

  public FourDiceCup(List<SixSidedDice> dice) {
    if (dice.size() != 4) {
      throw new IllegalArgumentException("Cup must have 2 dieces");
    }
    this.dieces = dice;
  }

  public GameResult swing() {
    var result = new FourDiceGameResult();
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
