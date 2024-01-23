package kata;

import kata.position.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class PlayingCards {
    private final List<Card> cards;
    private final Set<Position> excludedPositions = new HashSet<>();

    public PlayingCards() {
        this.cards = new ArrayList<Card>();
    }

    public int score() {
        int sum = 0;
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.flipped() && !excludedPositions.contains(Position.fromIndex(i))) {
                int value = card.value();
                sum += value;
            }
        }
        return sum;
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
        if (this.cards.get(0).value() == this.cards.get(4).value() && this.cards.get(4).value() == this.cards.get(8).value()) {
            excludedPositions.add(Position.fromIndex(0));
            excludedPositions.add(Position.fromIndex(4));
            excludedPositions.add(Position.fromIndex(8));
        }
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
