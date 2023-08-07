package kata;

public class Place {
    public static final Place GO = new Place("GO", 0, Integer.MAX_VALUE, Rent.getNull());
    public static Place MediterraneanAvenue = new Place("Mediterranean Avenue", 1, 60, Rent.getNull());
    public static Place CommunityChest = new Place("Community Chest", 2, Integer.MAX_VALUE, Rent.getNull());
    public static Place BalticAvenue = new Place("Baltic Avenue", 3, 60, new Rent(4, 20, 60, 180, 320, 450));
    public static Place IncomeTax = new Place("Income Tax", 4, Integer.MAX_VALUE, (p, __, ___) -> p.money -= 200);
    public static Place ReadingRailroad = new Place("Reading Railroad", 5, 200, Rent.getNull());
    public static Place OrientalAvenue = new Place("Oriental Avenue", 6, 100, Rent.getNull());
    public static Place Chance = new Place("Chance", 7, Integer.MAX_VALUE, ChancePile.get());
    public static Place VermontAvenue = new Place("Vermont Avenue", 8, 100, Rent.getNull());
    public static Place ConnecticutAvenue = new Place("Connecticut Avenue", 9, 120, Rent.getNull());
    public static Place Jail = new Place("Jail", 10, Integer.MAX_VALUE, Rent.getNull());

    private final String name;
    private final int location;
    private final int purchaseCost;
    private AutomaticAction automaticAction;

    public Place(String name, int location, int purchaseCost, AutomaticAction automaticAction) {
        this.automaticAction = automaticAction;
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

    public AutomaticAction getAutomaticAction() {
        return automaticAction;
    }

    public boolean isPurchasable() {
        return automaticAction instanceof Rent;
    }
}
