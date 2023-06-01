package kata;

import java.util.HashMap;
import java.util.Map;

public class Rent implements AutomaticAction {

    private final Map<Upgrades, Integer> rents = new HashMap<>();

    public Rent(int single, int oneHouse, int twoHouses, int threeHouses, int fourHouses, int hotel) {
        rents.put(Upgrades.SINGLE, single);
        rents.put(Upgrades.ALL, single * 2);
        rents.put(Upgrades.ONE_HOUSE, oneHouse);
        rents.put(Upgrades.TWO_HOUSES, twoHouses);
        rents.put(Upgrades.THREE_HOUSES, threeHouses);
        rents.put(Upgrades.FOUR_HOUSES, fourHouses);
        rents.put(Upgrades.HOTEL, hotel);
    }

    public static Rent getNull() {
        return new Rent(0, 0,0,0,0,0);
    }

    public int get(Upgrades propertyGroupImprovements) {
        return rents.getOrDefault(propertyGroupImprovements, 0);
    }

    @Override
    public void execute(Player player, Monopoly monopoly, Place place) {
        Player owner = monopoly.getOwner(place);
        if (owner != null && owner != player) {
            int rent = get(owner.getPropertyGroupImprovements(place));

            owner.money += rent;
            player.money -= rent;
        }
    }
}
