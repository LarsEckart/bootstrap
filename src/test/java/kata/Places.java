package kata;

class Places {
    public static final Places GO = new Places("GO");
    private final String name;

    public Places(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int location() {
        return 0;
    }
}
