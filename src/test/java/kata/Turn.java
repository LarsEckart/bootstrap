package kata;

public class Turn {
    private final Player player;
    private final Monopoly board;

    public Turn(Player player, Monopoly board) {
        this.player = player;
        this.board = board;
    }

    public Turn buyProperty() {
        if (board.isPropertyAvailable(player.location())) {
            Place property = board.getProperty(player.location());
            if(property.getPurchaseCost() <= player.getMoney()) {
                player.money = player.money - property.getPurchaseCost();
                player.properties.add(property);
            }
        }
        // if so, buy it
        // if not, do nothing
        return this;
    }

    public Monopoly endTurn() {
        board.endTurn();
        return board;
    }
}
