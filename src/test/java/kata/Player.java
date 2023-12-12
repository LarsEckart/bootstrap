package kata;

import kata.position.Position;

import java.util.ArrayList;
import java.util.List;

class Player {
  private List<Card> cards = new ArrayList<>();
  private String name;
  private Card pendingCard;

  public Player(String name) {
    this.name = name;
  }

  public int numberOfCards() {
    return cards.size();
  }

  public void addCard(Card card) {
    this.cards.add(card);
  }

  public int score() {
    return this.cards.stream().filter(card -> card.flipped()).mapToInt(Card::value).sum();
  }

  public void flipCard(Position position) {
    this.cards.get(position.toIndex()).flip();
  }

  public Card swap(Position position) {
    Card card = cards.get(position.toIndex());
    cards.set(position.toIndex(), pendingCard);
    pendingCard = null;
    return card;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append(this.name + ": " + "\n");

    // TODO: iterate over positions?
    for (int row = 1; row <= 3; row++) {
      for (int column = 1; column <= 4; column++) {
        sb.append(cards.get(Position.atRow(row).atColumn(column).toIndex()).toString());
        sb.append(" ");
      }
      sb.append("\n");
    }

    if (pendingCard != null) {
      sb.append("Pending card: ").append(this.pendingCard.value());
    }

    sb.append("\n");

    return sb.toString();
  }

  public String name() {
    return this.name;
  }

  public void acceptIncomingCard(Card card) {
    this.pendingCard = card;
  }

  public Card getPendingCard() {
    Card card = pendingCard;
    pendingCard = null;
    return card;
  }

  public boolean cardAlreadyFlipped(Position position) {
    return cards.get(position.toIndex()).flipped();
  }
}
