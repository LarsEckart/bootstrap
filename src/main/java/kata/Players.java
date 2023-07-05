package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Players {

    private final List<String> players;

    private Players(List<String> players) {
        List<String> strings = new ArrayList<>(players);// defensive copy (immutable

        Collections.shuffle(strings);
        this.players = strings;
    }

    public static Players of(String... players) {
        List<String> list = Arrays.stream(players).toList();
        return new Players(list);
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public String getCurrentPlayer() {
        return players.get(0);
    }

    public List<String> all() {
        return List.copyOf(players);
    }

}
