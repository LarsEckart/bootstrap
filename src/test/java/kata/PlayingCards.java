package kata;

import kata.position.Position;

import java.util.ArrayList;
import java.util.List;

final class PlayingCards {
    private final List<Card> cards;

    public PlayingCards() {
        this.cards = new ArrayList<Card>();
    }

    public int score() {
        return cards.stream().filter(Card::flipped).mapToInt(Card::value).sum();
    }

    public int numberOfCards() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void flipCard(Position position) {
        cards.get(position.toIndex()).flip();
    }

    public Card swap(Position position, Card newCard) {
        Card card = cards.get(position.toIndex());
        cards.set(position.toIndex(), newCard);
        return card;
    }

    public boolean cardAlreadyFlipped(Position position) {
        return cards.get(position.toIndex()).flipped();
    }

    public boolean allCardsFlipped() {
        return cards.stream().allMatch(Card::flipped);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
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
