package kata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Deck {
  int numberCards = 150;

  List<Card> list = new ArrayList<>();

  public Deck() {
    Map<Points, Integer> map = new LinkedHashMap<>();
    map.put(Points.of(-2), 5);
    map.put(Points.of(-1), 10);
    map.put(Points.of(0), 15);
    map.put(Points.of(1), 10);
    map.put(Points.of(2), 10);
    map.put(Points.of(3), 10);
    map.put(Points.of(4), 10);
    map.put(Points.of(5), 10);
    map.put(Points.of(6), 10);
    map.put(Points.of(7), 10);
    map.put(Points.of(8), 10);
    map.put(Points.of(9), 10);
    map.put(Points.of(10), 10);
    map.put(Points.of(11), 10);
    map.put(Points.of(12), 10);

    fillDeck(map);
  }

  private void fillDeck(Map<Points, Integer> kvMap) {
    kvMap.forEach(this::fillDeck);
  }

  private void fillDeck(Points value, int amount) {
    for (int i = 0; i < amount; i++) {
      list.add(new Card(value));
    }
  }

  public Card takeFromTop() {
    Card card = list.get(--numberCards);
    card.flip();
    return card;
  }

  public int numberOfCards() {
    return numberCards;
  }

  public Card dealFromTop() {
    return list.get(--numberCards);
  }
}
