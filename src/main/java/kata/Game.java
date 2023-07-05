package kata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game {
    private final Players players;
    private final Map<Player, Integer> locations = new HashMap<>();
    private final List<Field> fields = List.of(
            Field.of("GO"),
            Field.of("Mediterranean Avenue"),
            Field.of("Community Chest"),
            Field.of("Baltic Avenue"),
            Field.of("Income Tax"),
            Field.of("Reading Railroad"),
            Field.of("Oriental Avenue"),
            Field.of("Chance"),
            Field.of("Vermont Avenue"),
            Field.of("Connecticut Avenue"),
            Field.of("Jail"),
            Field.of("St. Charles Place"),
            Field.of("Electric Company"),
            Field.of("States Avenue"),
            Field.of("Virginia Avenue"),
            Field.of("Pennsylvania Railroad"),
            Field.of("St. James Place"),
            Field.of("Community Chest"),
            Field.of("Tennessee Avenue"),
            Field.of("New York Avenue"),
            Field.of("Free Parking"),
            Field.of("Kentucky Avenue"),
            Field.of("Chance"),
            Field.of("Indiana Avenue"),
            Field.of("Illinois Avenue"),
            Field.of("B. & O. Railroad"),
            Field.of("Atlantic Avenue"),
            Field.of("Ventnor Avenue"),
            Field.of("Water Works"),
            Field.of("Marvin Gardens"),
            Field.of("Go To Jail"),
            Field.of("Pacific Avenue"),
            Field.of("North Carolina Avenue"),
            Field.of("Community Chest"),
            Field.of("Pennsylvania Avenue"),
            Field.of("Short Line"),
            Field.of("Chance"),
            Field.of("Park Place"),
            Field.of("Luxury Tax"),
            Field.of("Boardwalk")
    );

    private Game(Players players) {
        this.players = players;
        for (Player player : players.all()) {
            locations.put(player, fields.get(0).getIndex());
        }
    }

    public static Game of(Players players) {
        return new Game(players);
    }

    public boolean canBePlayed() {
        return players.numberOfPlayers() >= 2 && players.numberOfPlayers() <= 8;
    }

    public Player whoseTurnIsIt() {
        return players.getCurrentPlayer();
    }

    public int locationOfCurrentPlayer() {
        return locations.get(players.getCurrentPlayer());
    }

    public void playerRolled(int roll) {
        Player currentPlayer = players.getCurrentPlayer();
        int currentLocation = locations.get(currentPlayer);
        int newLocationIndex = (currentLocation + roll) % 40;
        locations.put(currentPlayer, newLocationIndex);
        if (newLocationIndex == 0) {
            currentPlayer.addMoney(200);
        }
    }

    public void endTurn() {
        players.nextPlayer();
    }

    public int balanceOfCurrentPlayer() {
        return players.getCurrentPlayer().balance();
    }
}
