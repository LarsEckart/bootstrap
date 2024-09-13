package kata;

import java.util.Random;

public class SixSidedDice {

  private final Random random;

  public SixSidedDice() {
    this.random = new Random();
  }

  public DiceResult roll() {
    return new DiceResult(random.nextInt(6) + 1);
  }
}
