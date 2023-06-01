package kata;

class Place {
    public static final Place GO = new Place("GO", 0, Integer.MAX_VALUE);
    public static Place MediterraneanAvenue = new Place("Mediterranean Avenue", 1, 60);
    public static Place CommunityChest = new Place("Community Chest", 2, Integer.MAX_VALUE);
    public static Place BalticAvenue = new Place("Baltic Avenue", 3, 60);
    public static Place IncomeTax = new Place("Income Tax", 4, Integer.MAX_VALUE);
    public static Place ReadingRailroad = new Place("Reading Railroad", 5, 200);
    public static Place OrientalAvenue = new Place("Oriental Avenue", 6, 100);
    public static Place Chance = new Place("Chance", 7, Integer.MAX_VALUE);
    public static Place VermontAvenue = new Place("Vermont Avenue", 8, 100);
    public static Place ConnecticutAvenue = new Place("Connecticut Avenue", 9, 120);

    private final String name;
    private final int location;
    private final int purchaseCost;

    public Place(String name, int location, int purchaseCost) {
        this.name = name;
        this.location = location;
        this.purchaseCost = purchaseCost;
    }

    public String name() {
        return name;
    }

    public int location() {
        return location;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }
}
