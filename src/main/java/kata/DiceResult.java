package kata;

public class DiceResult {

  private final int result;

  public DiceResult(int result) {
    if (result < 1) {
      throw new IllegalArgumentException("Dice result must be positive");
    }
    if (result > 6) {
      throw new IllegalArgumentException("Dice result must be less than 7");
    }
    this.result = result;
  }

  public int asInt() {
    return result;
  }
}
