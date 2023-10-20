package kata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.Test;

@UseReporter({IntelliJReporter.class, AutoApproveReporter.class})
class PlayerTest {

  @Test
  void initially_player_has_score_zero() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    assertEquals(0, player.score());
  }

  @Test
  void after_flipping_one_card_player_has_a_score_of_twelve() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 1);

    assertEquals(12, player.score());
  }

  @Test
  void printFlippedCard() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 1);

    Approvals.verify(player.toString());
  }

  @Test
  void players_flips_card_in_row_1_column_1() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 1);

    Approvals.verify(player.toString());
  }

  @Test
  void players_flips_card_in_row_1_column_2() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 2);

    Approvals.verify(player.toString());
  }

  @Test
  void player_flips_card_in_row_1_column_3() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 3);

    Approvals.verify(player.toString());
  }

  @Test
  void player_flips_card_in_row_1_column_4() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 4);

    Approvals.verify(player.toString());
  }

  @Test
  void after_flipping_two_cards_the_score_is_twenty_four() {
    Player player = new Player("any");
    Deck deck = new Deck();
    for (int i = 0; i < 12; i++) {
      player.addCard(deck.takeFromTop());
    }

    player.flipCard(1, 1);
    player.flipCard(1, 2);

    assertEquals(24, player.score());
  }
}
