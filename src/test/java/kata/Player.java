package kata;

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

    public void flipCard(int row, int column) {
        this.cards.get(toIndex(row, column)).flip();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.name + ": " + "\n");

        // print cards in a 3x4 grid
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 4; column++) {
                sb.append(cards.get(toIndex(row, column)).toString());
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

    private static int toIndex(int row, int column) {
        return (row - 1) * 3 + column;
    }

    public String name() {
        return this.name;
    }

    public void acceptIncomingCard(Card card) {
        this.pendingCard = card;
    }
}
