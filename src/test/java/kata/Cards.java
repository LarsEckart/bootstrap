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
        if (row == 1 && column == 1) {
            cards.get(0).flip();
        }
        if (row == 1 && column == 2) {
            cards.get(1).flip();
        }
        if (row == 1 && column == 3) {
            cards.get(2).flip();
        }
        if (row == 1 && column == 4) {
            cards.get(3).flip();
        }
        if (row == 2 && column == 1) {
            cards.get(4).flip();
        }
        if (row == 2 && column == 2) {
            cards.get(5).flip();
        }
        if (row == 2 && column == 3) {
            cards.get(6).flip();
        }
        if (row == 2 && column == 4) {
            cards.get(7).flip();
        }
        if (row == 3 && column == 1) {
            cards.get(8).flip();
        }
        if (row == 3 && column == 2) {
            cards.get(9).flip();
        }
        if (row == 3 && column == 3) {
            cards.get(10).flip();
        }
        if (row == 3 && column == 4) {
            cards.get(11).flip();
        }
    }
}
