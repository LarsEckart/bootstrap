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
            monopoly.moveTo(Place.StCharlesPlace);
        }
    }

    public static class GoBack3Spaces extends ChanceCard {

        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
            monopoly.move(-3);
        }
    }

    public static class SpeedingFine extends ChanceCard {

        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
            player.money -= 15;
        }
    }

    public static class ChairmanOfTheBoard extends ChanceCard {

        @Override
        public void execute(Player currentPlayer, Monopoly monopoly, Place place) {
            monopoly.players.where(p -> p != currentPlayer).forEach(p -> {
                p.money += 50;
                currentPlayer.money -= 50;
            });
        }
    }

    public static class GetOutOfJailFree extends ChanceCard {
        @Override
        public void execute(Player player, Monopoly monopoly, Place place) {
//            throw new UnsupportedOperationException("continue here");
        }
    }
}
