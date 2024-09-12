package kata;

class DiceGame {

  private final Cup cup;

  public DiceGame(Cup cup1) {
    this.cup = cup1;
  }

  public GameResult play() {
    return cup.swing();
  }
}
