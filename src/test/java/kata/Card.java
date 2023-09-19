package kata;

class Card {
    private final Points points;

    Card(Points points) {
        this.points = points;
    }

    public int value() {
        return this.points.value();
    }
}
