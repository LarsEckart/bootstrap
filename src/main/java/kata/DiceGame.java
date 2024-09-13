package kata;

class DiceGame {

  private final Cup cup;

  public DiceGame(Cup cup) {
    this.cup = cup;
  }

  public GameResult play() {
    return cup.swing();
  }
}
