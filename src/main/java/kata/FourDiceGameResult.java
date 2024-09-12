package kata;

import java.util.Objects;

public class FourDiceGameResult implements GameResult {

  private int value = 0;

  public FourDiceGameResult() {}

  public FourDiceGameResult(int initialValue) {
    value = initialValue;
  }

  @Override
  public void add(DiceResult roll) {
    value = value + roll.asInt();
  }

  @Override
  public boolean hasWon() {
    System.out.println("value: " + value);
    return value >= 14;
  }

  @Override
  public boolean equals(Object other) {
    return other == this
        || (other instanceof FourDiceGameResult gameResult)
            && Objects.equals(value, gameResult.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
