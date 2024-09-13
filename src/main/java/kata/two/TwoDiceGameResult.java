package kata.two;

import java.util.Objects;
import kata.DiceResult;
import kata.GameResult;

public class TwoDiceGameResult implements GameResult {

  private int value = 0;

  public TwoDiceGameResult() {}

  public TwoDiceGameResult(int initialValue) {
    value = initialValue;
  }

  @Override
  public void add(DiceResult roll) {
    value = value + roll.asInt();
  }

  @Override
  public boolean hasWon() {
    System.out.println("value: " + value);
    return value >= 7;
  }

  @Override
  public boolean equals(Object other) {
    return other == this
        || (other instanceof TwoDiceGameResult gameResult)
            && Objects.equals(value, gameResult.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
