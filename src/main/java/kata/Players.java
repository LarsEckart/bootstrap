package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Players {

    private final List<Player> playersNew;

    private Players(List<String> players) {
        List<String> strings = new ArrayList<>(players);// defensive copy (immutable
        Collections.shuffle(strings);
        this.playersNew = strings.stream().map(Player::new).toList();
    }

    public static Players of(String... players) {
        List<String> list = Arrays.stream(players).toList();
        return new Players(list);
    }

    public int numberOfPlayers() {
        return playersNew.size();
    }

    public String getCurrentPlayer() {
        return playersNew.get(0).getName();
    }

    public List<String> all() {
        return playersNew.stream().map(Player::getName).toList();
    }
}
