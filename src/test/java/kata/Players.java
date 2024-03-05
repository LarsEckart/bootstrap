package kata;

import java.util.ArrayList;
import java.util.List;

public final class Players {

  private final List<Player> players;
  private Player currentPlayer;

  public Players() {
    this.players = new ArrayList();
  }

  public void addPlayer(Player player) {
    if (players.isEmpty()) {
      currentPlayer = player;
    }
    players.add(player);
  }

  public void acceptIncomingCard(Card card) {
    this.currentPlayer.addCard(card);
    this.currentPlayer = nextPlayer();
  }

  public Player playerWithHighestScore() {
    Player highest = players.getFirst();
    for (Player player : players) {
      if (player.score() > highest.score()) {
        highest = player;
      }
    }

    return highest;
  }

  public boolean allFinished() {
    return players.stream().filter(Player::isFinishedPlaying).allMatch(Player::playedLastTurn);
  }

  public Player nextPlayer() {
    int i = players.indexOf(currentPlayer);
    System.out.println(i);
    int indexOfNextPlayer = (i + 1) % players.size();
    System.out.println(indexOfNextPlayer);
    return players.get(indexOfNextPlayer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Player player : players) {
      sb.append(player.toString());
      sb.append("\n");
    }

    return sb.toString();
  }
}
