package kata;

class Places {
    public static final Places GO = new Places("GO", 0);
    public static Places MediterraneanAvenue = new Places("Mediterranean Avenue", 1);
    public static Places CommunityChest = new Places("Community Chest", 2);
    public static Places BalticAvenue = new Places("Baltic Avenue", 3);
    public static Places IncomeTax = new Places("Income Tax", 4);
    public static Places ReadingRailroad = new Places("Reading Railroad", 5);

    private final String name;
    private final int location;

    public Places(String name, int location) {
        this.name = name;
        this.location = location;
    }

    public String name() {
        return name;
    }

    public int location() {
        return location;
    }
}
