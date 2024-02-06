package kata;

import kata.position.Position;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class PlayingCards {
    private final Map<Position, Card> cards;
    private int currentCardCount = 0;

    public PlayingCards() {
        this.cards = new LinkedHashMap<>();
    }

    public int score() {
        int sum = 0;
        for (Map.Entry<Position, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            if (card.flipped()) {
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
        cards.put(Position.fromIndex(currentCardCount), card);
        currentCardCount++;
    }

    public Optional<Card> flipCard(Position position) {
        cards.get(position).flip();
        return checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring();

    }

    public Card swap(Position position, Card newCard) {
        Card card = cards.get(position);
        cards.put(position, newCard);
        return checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring().orElse(card);
    }

    private Optional<Card> checkIfThreeInAVerticalRowAndIfSoExcludeThemFromScoring() {
        for (int i = 1; i <= 4; i++) {
            if (allTheSame(Position.allInVerticalRow(i))) {
                Set<Position> c = Position.allInVerticalRow(i);
                Card card = cards.get(c.iterator().next());
                c.forEach(p -> cards.put(p, Card.noCard()));
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    private boolean allTheSame(Set<Position> positions) {
        int pointsOfFirstCard = cards.get(positions.iterator().next()).value();
        List<Card> cardsInVerticalRow = positions.stream().map(cards::get).toList();
        long howManyFlipped = cardsInVerticalRow.stream().filter(Card::flipped).count();
        return cardsInVerticalRow.stream().filter(Card::flipped).allMatch(p -> p.value() == pointsOfFirstCard) && howManyFlipped == 3;
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
                Position position = Position.atRow(row).atColumn(column);
                sb.append(cards.get(position).toString());
                sb.append(" ");
            }

            sb.append("\n");
        }
        return sb.toString();
    }
}
