package kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Deck {
  private final List<Card> cards;

  public Deck() {
    cards = new ArrayList<>();
    initializeStandardDeck();
  }

  private void initializeStandardDeck() {
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
  }

  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }

  public int size() {
    return cards.size();
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  public Optional<Card> deal() {
    if (cards.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(cards.remove(cards.size() - 1));
  }
}
