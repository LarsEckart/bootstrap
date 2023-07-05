package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Players {

    private final List<Player> players;
    private int currentPlayerIndex = 0;

    private Players(List<String> names) {
        List<String> strings = new ArrayList<>(names);
        this.players = strings.stream().map(Player::new).toList();
    }

    public static Players of(String... names) {
        List<String> list = new ArrayList<>(Arrays.asList(names));
        Collections.shuffle(list);
        return new Players(list);
    }

    public static Players ofFixedOrder(String... names) {
        List<String> list = Arrays.asList(names);
        return new Players(list);
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> all() {
        return List.copyOf(players);
    }

    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }
}
