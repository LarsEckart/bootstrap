package kata;

import java.util.HashMap;
import java.util.Map;

class Game {
    private final Players players;
    private final Map<String, Integer> locations = new HashMap<>();

    private Game(Players players) {
        this.players = players;
        for (String player : players.all()) {
            locations.put(player, 0);
        }
    }

    public static Game of(Players players) {
        return new Game(players);
    }

    public boolean canBePlayed() {
        return players.numberOfPlayers() >= 2 && players.numberOfPlayers() <= 8;
    }

    public String whoseTurnIsIt() {
        return players.getCurrentPlayer();
    }

    public int locationOfCurrentPlayer() {
        return locations.get(players.getCurrentPlayer());
    }

    public void playerRolled(int roll) {
        String currentPlayer = players.getCurrentPlayer();
        int currentLocation = locations.get(currentPlayer);
        locations.put(currentPlayer, currentLocation + roll);
    }
}
