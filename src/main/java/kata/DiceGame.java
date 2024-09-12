package kata;

class DiceGame {

  private final Dice dice;

  public DiceGame(Dice dice) {
    this.dice = dice;
  }

  public int play() {
    int result = dice.roll();
    return result;
  }
}
