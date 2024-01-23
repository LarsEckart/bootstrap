package kata;

import kata.position.Position;

import java.util.List;

final class PlayingCards {
    private final List<Card> cards;

    public PlayingCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }

    public int score() {
        return cards().stream().filter(card -> card.flipped()).mapToInt(Card::value).sum();
    }

    public int numberOfCards() {
        return cards.size();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void flipCard(Position position) {
        this.cards.get(position.toIndex()).flip();
    }

    public Card swap(Position position, Card newCard) {
        Card card = cards.get(position.toIndex());
        cards.set(position.toIndex(), newCard);
        return card;
    }

    @Override
    public String toString() {
        var sb = new StringBuffer();
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 4; column++) {
                sb.append(cards.get(Position.atRow(row).atColumn(column).toIndex()).toString());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
