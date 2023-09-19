package kata;

class Card {
    private final Points points;
    private boolean flipped;

    Card(Points points) {
        this.points = points;
    }

    public int value() {
        return this.points.value();
    }

    public void flip() {
        this.flipped = true;
    }

    public boolean flipped() {
        return this.flipped;
    }
}
