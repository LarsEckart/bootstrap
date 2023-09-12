package kata;

import java.util.ArrayList;
import java.util.List;

class Skyjo {
    private final Deck deck;
    private List<Player> players = new ArrayList<>();

    public Skyjo(Deck deck) {
        this.deck = deck;
    }

    public void deal() {
        for (Player player : players) {
            for (int i = 0; i < 12; i++) {
                player.addCard(this.deck.takeFromTop());
            }
        }
    }

    public void registerPlayer(Player alice) {
        this.players.add(alice);
    }
}
