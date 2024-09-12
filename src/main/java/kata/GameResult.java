package kata;

import java.util.Objects;

class GameResult {

  private int value = 0;

  public GameResult() {}

  public GameResult(int initialValue) {
    value = initialValue;
  }

  public void add(DiceResult roll) {
    value = value + roll.asInt();
  }

  @Override
  public boolean equals(Object other) {
    return other == this
        || (other instanceof GameResult gameResult) && Objects.equals(value, gameResult.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
