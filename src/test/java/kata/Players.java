package kata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

class Players implements Iterable<Player>{

    private List<Player> players = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Player> spliterator() {
        return Iterable.super.spliterator();
    }

    public Player withHighestScore() {
        // find player with highest score
        int indexFound = -1;
        int lowestScore = -4;
        for (Player player : players) {
            if (player.score() > lowestScore) {
                indexFound = players.indexOf(player);
                lowestScore = player.score();
            }
        }
        return players.get(indexFound);
    }
}
