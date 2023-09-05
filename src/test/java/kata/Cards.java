package kata;

import java.util.ArrayList;
import java.util.List;

class Cards {

    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        if (cards.size() != 12) {
            return "no cards yet\n";
        }
        var strb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                strb.append(cards.get(count++));
            }
            strb.append("\n");
        }
        return strb.toString();
    }

    public void flip(int row, int column) {
        int index = (row - 1) * 4 + column - 1;
        if (index >= 0 && index < cards.size()) {
            cards.get(index).flip();
        }
    }

    public int score() {
        return cards.stream().filter(c -> c.flipped()).map(Card::value).reduce(0, Integer::sum);
    }
}
