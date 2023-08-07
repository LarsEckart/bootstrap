package kata;

public abstract class ChanceCard implements AutomaticAction {

    public static class BankCard extends ChanceCard {

        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
            player.money += 50;
        }
    }

    public static class GoToStCharlesPlace extends ChanceCard {

        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
            player.move(Place.StCharlesPlace);
        }
    }
}
