package kata;

import java.util.ArrayList;
import java.util.List;

public class Hand {
  private final List<Card> cards;

  public Hand() {
    this.cards = new ArrayList<>();
  }

  public Hand(List<Card> cards) {
    this.cards = new ArrayList<>(cards);
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public int size() {
    return cards.size();
  }

  public Score getScore() {
    return Score.calculate(cards);
  }

  public boolean beats(Hand other) {
    var thisScore = this.getScore();
    var otherScore = other.getScore();

    if (thisScore.isBust() && otherScore.isBust()) {
      return false;
    }

    if (thisScore.isBust()) {
      return false;
    }

    if (otherScore.isBust()) {
      return true;
    }

    boolean thisIsBlackjack = thisScore.isBlackjack(this.size());
    boolean otherIsBlackjack = otherScore.isBlackjack(other.size());

    if (thisIsBlackjack && !otherIsBlackjack) {
      return true;
    }

    if (!thisIsBlackjack && otherIsBlackjack) {
      return false;
    }

    return thisScore.getValue() > otherScore.getValue();
  }

  @Override
  public String toString() {
    return "Hand{cards=" + cards.size() + ", score=" + getScore() + "}";
  }
}
