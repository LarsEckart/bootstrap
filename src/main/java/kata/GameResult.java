package kata;

public interface GameResult {

  void add(DiceResult roll);

  boolean hasWon();
}
