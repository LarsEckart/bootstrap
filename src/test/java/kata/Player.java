package kata;

import kata.position.Position;

import java.util.ArrayList;
import java.util.List;

class Player {
  private List<Card> cards = new ArrayList<>();
  private String name;
  private Card pendingCard;
  public boolean playedLastTurn;
  public PlayingCards playingCards;

  public Player(String name) {
    this.name = name;
    this.playingCards = new PlayingCards(this.cards);
  }

  public int numberOfCards() {
    return playingCards.numberOfCards();
  }

  public void addCard(Card card) {
    this.playingCards.addCard(card);
  }

  public int score() {
    return playingCards.score();
  }

  public void flipCard(Position position) {
    this.playingCards.flipCard(position);
  }

  public Card swap(Position position) {
    Card card = this.playingCards.swap(position, pendingCard);
    pendingCard = null;
    return card;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append(this.name + ": " + "\n");

    sb.append(this.playingCards);

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

  public boolean allCardsFlipped() {
    return cards.stream().allMatch(Card::flipped);
  }

}
