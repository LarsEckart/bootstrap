package kata;

import java.util.HashMap;
import java.util.Map;

class Game {
    private final Players players;
    private final Map<Player, Integer> locations = new HashMap<>();

    private Game(Players players) {
        this.players = players;
        for (Player player : players.all()) {
            locations.put(player, 0);
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
        locations.put(currentPlayer, (currentLocation + roll) % 40);
    }
}
