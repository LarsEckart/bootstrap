package kata;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

class Player {
    private List<Card> cards = new ArrayList<>();

    public int numberOfCards() {
        return cards.size();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
