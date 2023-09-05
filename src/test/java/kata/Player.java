package kata;

class Player {
    private final String name;
    private Cards cards = new Cards();

    public Player(String name) {
        this.name = name;
    }

    public void dealInitialCards(Card card) {
            cards.add(card);
    }

    @Override
    public String toString() {
        return name + "\n" + cards;
    }

    public void flip(int row, int column) {
        cards.flip(row, column);
    }

    public int score() {
        return cards.score();
    }

    public String name() {
        return name;
    }
}
