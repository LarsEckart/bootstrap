package kata;

import java.util.List;

public class Score {
  private final int value;
  private final boolean isSoft;

  private Score(int value, boolean isSoft) {
    this.value = value;
    this.isSoft = isSoft;
  }

  public static Score calculate(List<Card> cards) {
    int total = 0;
    int aces = 0;

    for (Card card : cards) {
      var rank = card.getRank();
      total += rank.getBlackjackValue();
      if (rank == Rank.ACE) {
        aces++;
      }
    }

    while (total > 21 && aces > 0) {
      total -= 10;
      aces--;
    }

    boolean isSoft = aces > 0 && total <= 21;
    return new Score(total, isSoft);
  }

  public int getValue() {
    return value;
  }

  public boolean isSoft() {
    return isSoft;
  }

  public boolean isBust() {
    return value > 21;
  }

  public boolean isBlackjack(int cardCount) {
    return cardCount == 2 && value == 21;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Score)) return false;
    var score = (Score) obj;
    return value == score.value && isSoft == score.isSoft;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(value, isSoft);
  }

  @Override
  public String toString() {
    return isSoft ? "Soft " + value : String.valueOf(value);
  }
}
