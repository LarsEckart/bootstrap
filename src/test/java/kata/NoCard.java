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
        throw new IllegalMoveException("Cannot flip a NoCard");
    }

    @Override
    public String toString() {
        return "|  |";
    }
}
