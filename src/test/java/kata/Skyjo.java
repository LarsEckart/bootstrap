package kata;

import java.util.ArrayList;
import java.util.List;

class Skyjo {
  private final Deck deck;
  private List<Player> players = new ArrayList<>();
  private DiscardPile discardPile;

  public Skyjo(Deck deck) {
    this.deck = deck;
  }

  public void deal() {
    for (Player player : players) {
      for (int i = 0; i < 12; i++) {
        player.addCard(this.deck.takeFromTop());
      }
    }

    discardPile = new DiscardPile(this.deck.takeFromTop());
  }

  public void registerPlayer(Player alice) {
    this.players.add(alice);
  }

  public Player nextPlayer() {
    Player highest = players.get(0);
    for (Player player : players) {
      if (player.score() > highest.score()) {
        highest = player;
      }
    }

    return highest;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Player player : players) {
      sb.append(player.toString());
      sb.append("\n");
    }

    sb.append("Discard pile: " + discardPile.toString());
    sb.append("\n");
    sb.append("Next player: " + nextPlayer().name());
    sb.append("\n");

    return sb.toString();
  }

  public void on(Event event) {
    var card = this.deck.takeFromTop();

    this.nextPlayer().acceptIncomingCard(card);
  }
}
