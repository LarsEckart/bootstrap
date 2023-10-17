package kata;

class DiscardPile {
    private final Card card;

    public DiscardPile(Card card) {
        this.card = card;
    }

    public Card takeFromTop() {
        return card;
    }

    @Override
    public String toString() {
        return "" + this.card.value();
    }
}
