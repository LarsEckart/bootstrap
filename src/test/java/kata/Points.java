package kata;

record Points(int value) {

    public static Points of(int value) {
        return new Points(value);
    }
}
