package kata;

import java.util.Objects;

class Card {
  private final Points points;
  private boolean flipped;

  private Card(Points points) {
    this.points = points;
  }

    static Card of(Points points) {
        return new Card(points);
    }

  public static Card faceUp(Points points) {
    Card card = new Card(points);
    card.flip();
    return card;
  }

  public int value() {
    return this.points.value();
  }

  public void flip() {
    this.flipped = true;
  }

  public boolean flipped() {
    return this.flipped;
  }

  @Override
  public String toString() {
    if (flipped) {
      if (points.value() < 10) {
        return "| " + points.value() + "|";
      }
      return "|" + points.value() + "|";
    }
    return "|XX|";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Card card = (Card) o;
    return Objects.equals(points, card.points);
  }

  @Override
  public int hashCode() {
    return Objects.hash(points);
  }
}
