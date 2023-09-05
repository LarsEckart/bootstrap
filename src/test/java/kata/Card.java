package kata;

class Card {
    private final int value;
    private boolean flipped = false;

    public Card(int value) {
        this.value = value;
    }

    public void flip() {
        flipped = !flipped;
    }

    @Override
    public String toString() {
        return flipped ? "<" + value + ">" : "<X" + value + "X>";
    }

    public int value() {
        return value;
    }

    public boolean flipped() {
        return flipped;
    }
}
