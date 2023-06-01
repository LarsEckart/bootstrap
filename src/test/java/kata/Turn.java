package kata;

public class Turn {
    private final Player player;
    private final Monopoly board;

    public Turn(Player player, Monopoly board) {
        this.player = player;
        this.board = board;
    }

    public Turn buyProperty() {
        // ask if the property is available to buy
        if (board.isPropertyAvailable(player.location())) {
            // get the cost of the property
            Place property = board.getProperty(player.location());
            // ask if the player has enough money
            if(property.getPurchaseCost() <= player.getMoney()) {
                // if so, subtract the cost from the player's money
                player.money = player.money - property.getPurchaseCost();
                player.properties.add(property);
            }
        }
        // if so, buy it
        // if not, do nothing
        return this;
    }

    public void endTurn() {

    }
}
