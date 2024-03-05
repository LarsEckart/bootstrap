package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class PlayersTest {


  @Test
  void applesauce() {
    Player alice = new Player("Alice");
    Player bob = new Player("Bob");
    Players players = new Players();
    players.addPlayer(alice);
    players.addPlayer(bob);

    players.acceptIncomingCard(new Card(Points.of(5)));

    assertThat(alice.numberOfCards()).isEqualTo(1);

  }

  @Test
  void applesauce2() {
    Player alice = new Player("Alice");
    Player bob = new Player("Bob");
    Players players = new Players();
    players.addPlayer(alice);
    players.addPlayer(bob);

    players.acceptIncomingCard(new Card(Points.of(5)));
    players.acceptIncomingCard(new Card(Points.of(5)));

    assertThat(bob.numberOfCards()).isEqualTo(1);

  }
}
