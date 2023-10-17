package kata;

import java.util.ArrayList;
import java.util.List;

class Player {
    private List<Card> cards = new ArrayList<>();
    private String name;

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

    public void flipCard(int row, int column) {
        this.cards.get(row * 3 + column).flip();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.name + ": " + "\n");

        // print cards in a 3x4 grid
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 3; column++) {
                sb.append(cards.get(row * 3 + column).toString());
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String name() {
        return this.name;
    }
}
