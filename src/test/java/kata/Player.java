package kata;

class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int location() {
        return 0;
    }

    public String details() {
        return this.name + " ($1500)";
    }
}
