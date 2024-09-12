package kata;

class GameResult {

  private int result = 0;

  public void add(DiceResult roll) {
    result = result + roll.asInt();
  }

  public int asInt() {
    return result;
  }
}
