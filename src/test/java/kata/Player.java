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

    public void flipCard(int row, int column) {
        if (row == 1 && column == 1) {
            this.cards.get(0).flip();
        } else if (row == 1 && column == 2) {
            this.cards.get(1).flip();
        }
    }
}
