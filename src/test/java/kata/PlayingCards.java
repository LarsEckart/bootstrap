package kata;

import kata.position.Position;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class PlayingCards {
    private final Map<Position, Card> cards;
    private int index = 0;
    private final Set<Position> excludedPositions = new HashSet<>();

    public PlayingCards() {
        this.cards = new LinkedHashMap<>();
    }

    public int score() {
        int sum = 0;
        for (Map.Entry<Position, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            if (card.flipped() && !excludedPositions.contains(entry.getKey())) {
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
        cards.put(Position.fromIndex(index), card);
        index++;
    }

    public void flipCard(Position position) {
        cards.get(position).flip();
        checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring();
    }

    public Card swap(Position position, Card newCard) {
        Card card = cards.get(position);
        cards.put(position, newCard);
        checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring();

        return card;
    }

    private void checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring() {
        for (int i = 1; i <= 4; i++) {
            if (allTheSame(Position.allInVerticalRow(i))) {
                excludedPositions.addAll(Position.allInVerticalRow(i));
            }
        }
    }

    private boolean allTheSame(Set<Position> positions) {
        int pointsOfFirstCard = cards.get(positions.iterator().next()).value();
        List<Card> cardsInVerticalRow = positions.stream().map(cards::get).toList();
        long howManyFlippped = cardsInVerticalRow.stream().filter(Card::flipped).count();
        return cardsInVerticalRow.stream().filter(Card::flipped).allMatch(p -> p.value() == pointsOfFirstCard) && howManyFlippped == 3;
    }

    public boolean cardAlreadyFlipped(Position position) {
        return cards.get(position).flipped();
    }

    public boolean allCardsFlipped() {
        return cards.values().stream().allMatch(Card::flipped);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 4; column++) {
                sb.append(cards.get(Position.atRow(row).atColumn(column)).toString());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}