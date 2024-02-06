package kata;

class NoCard extends Card{
    private NoCard(Points points) {
        super(points);
    }

    static NoCard create() {
        return new NoCard(Points.of(0));
    }

    @Override
    public boolean flipped() {
        return true;
    }

    @Override
    public void flip() {
        // illegal move, card is gone, cannot be flipped.
        // who will validate that it doesnt happen, or do we let it happen and throw?
    }

    @Override
    public String toString() {
        return "|  |";
    }
}
