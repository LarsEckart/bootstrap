package kata;

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

    public int score() {
        return this.cards.stream().filter(card -> card.flipped()).mapToInt(Card::value).sum();
    }

    public void flipCard(int column, int row) {
        if (column == 1 && row == 1) {
            this.cards.get(0).flip();
        }
    }
}
