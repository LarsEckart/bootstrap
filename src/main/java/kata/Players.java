package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Players {

    private final List<Player> players;

    private Players(List<String> names) {
        List<String> strings = new ArrayList<>(names);
        Collections.shuffle(strings);
        this.players = strings.stream().map(Player::new).toList();
    }

    public static Players of(String... names) {
        List<String> list = Arrays.stream(names).toList();
        return new Players(list);
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public String getCurrentPlayerName() {
        return players.get(0).getName();
    }

    public Player getCurrentPlayer() {
        return players.get(0);
    }

    public List<Player> all() {
        return List.copyOf(players);
    }
}
