package kata;

public class Card {
  private final Suit suit;
  private final Rank rank;

  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  public Suit getSuit() {
    return suit;
  }

  public Rank getRank() {
    return rank;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    var card = (Card) obj;
    return suit == card.suit && rank == card.rank;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(suit, rank);
  }

  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}
