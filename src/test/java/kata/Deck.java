package kata;

import java.util.ArrayList;
import java.util.List;

class Deck {
    int numberCards = 150;

    List<Card> list = new ArrayList<>();

    public Deck() {
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 10; j++) {
                list.add(new Card(i));
            }
        }

        for (int i = 0; i < 15; i++) {
            list.add(new Card(0));
        }

        for (int i = 0; i < 10; i++) {
            list.add(new Card(-1));
        }
        for (int i = 0; i < 5; i++) {
            list.add(new Card(-2));
        }
    }

    public Card takeFromTop() {
        return list.get(--numberCards);
    }

    public int numberOfCards() {
        return numberCards;
    }
}
