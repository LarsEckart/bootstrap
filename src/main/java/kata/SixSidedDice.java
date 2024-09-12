package kata;

import java.util.Random;

class SixSidedDice {

  Random random;

  public SixSidedDice() {
    this.random = new Random();
  }

  public DiceResult roll() {
    return new DiceResult(random.nextInt(6) + 1);
  }
}
