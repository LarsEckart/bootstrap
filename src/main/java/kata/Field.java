package kata;

class Field {
    private final String name;
    private final int index;

    private Field(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static Field of(String name) {
        return new Field(name, 0);
    }

    public Integer getIndex() {
        return index;
    }
}
