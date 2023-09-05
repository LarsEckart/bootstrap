package kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Deck {

    private List<Card> deck = new ArrayList<>();

    public Deck() {
        for (int i = 0; i < 5; i++) {
            deck.add(new Card(-2));
        }
        for (int i = 0; i < 15; i++) {
            deck.add(new Card(0));
        }

        for (int i = 1; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                deck.add(new Card(i));
            }
        }
        for (int i = 0; i < 10; i++) {
            deck.add(new Card(-1));
        }

    }

    public int cardCount() {
        return deck.size();
    }

    public void shuffle() {
        // TODO; remove fixed random seed
        Collections.shuffle(deck, new Random(42));
    }

    public Card pop() {
        return deck.remove(0);
    }
}
