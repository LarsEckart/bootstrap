package kata;

public class ChanceCard {

    public static class BankCard implements AutomaticAction {

        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
            player.money += 50;
        }
    }
}
