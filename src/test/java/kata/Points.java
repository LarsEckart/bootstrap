package kata;

class Points {
    private final int value;

    private Points(int value) {
        this.value = value;
    }

    public static Points of(int value) {
        return new Points(value);
    }

    public int value() {
        return this.value;
    }
}
