package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kata.position.Position;
import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// @ExtendWith(TestCommitRevertMainExtension.class)
// @UseReporter(BeyondCompareMacReporter.class)
class SkyjoTest {

  @Test
  void deals_12_cards_to_each_player() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    skyjo.deal();

    assertThat(Alice.numberOfCards()).isEqualTo(12);
    assertThat(Bob.numberOfCards()).isEqualTo(12);
  }

  @Test
  void player_cannot_flip_card_that_is_already_flipped() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    var storyboard = new StoryBoard();
    skyjo.deal();
    storyboard.add(skyjo);

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();

    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(
            () -> skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.atRow(1).atColumn(1))))
        .withMessage("Cannot flip card that is already flipped.");

  }

  @Test
  @Disabled("finish logic for handling last turn")
  void game_can_be_played_until_end() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    skyjo.deal();

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    skyjo.start();

    for (int i = 2; i < 12; i++) {
      System.out.println(skyjo);
      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerPutsCardOnDiscardPile());
      skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.fromIndex(i)));

      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerPutsCardOnDiscardPile());
      skyjo.on(new PlayerFlipsCardDuringGame(Bob, Position.fromIndex(i)));
    }

    assertThat(skyjo.gameFinished()).isTrue();
  }

  @Test
  void game_play() {
    // problem: test passes but from looking at test we dont understand why.
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    var storyboard = new StoryBoard();
    skyjo.deal();
    storyboard.add(skyjo);

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDeck());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(Position.atRow(1).atColumn(1)));
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDeck());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(Position.atRow(1).atColumn(3)));
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDiscardPile());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(Position.atRow(1).atColumn(3)));
    storyboard.add(skyjo);

    // Player takes card from deck, puts it on discard pile, and flips one of their own cards.
    skyjo.on(new PlayerTakesCardFromDeck());
    skyjo.on(new PlayerPutsCardOnDiscardPile());
    storyboard.add(skyjo);
    skyjo.on(new PlayerFlipsCardDuringGame(Bob, Position.atRow(1).atColumn(4)));
    storyboard.add(skyjo);

    Approvals.verify(storyboard);
  }

  @Test
  void when_three_cards_in_vertical_row_are_the_same_then_player_puts_them_on_discrad_pile() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    var storyboard = new StoryBoard();
    skyjo.deal();
    storyboard.add(skyjo);

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(2).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();

    // Player takes card from deck, puts it on discard pile, and flips one of their own cards.
    skyjo.on(new PlayerTakesCardFromDeck());
    skyjo.on(new PlayerPutsCardOnDiscardPile());
    storyboard.add(skyjo);
    skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.atRow(3).atColumn(1)));
    storyboard.add(skyjo);

    Approvals.verify(storyboard);
  }

  @Test
  void quick_game_to_determine_if_game_is_over() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    var storyboard = new StoryBoard();
    skyjo.deal();
    storyboard.add(skyjo);

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();

    for (int i = 2; i < 12; i++) {
      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerPutsCardOnDiscardPile());
      skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.fromIndex(i)));

      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerPutsCardOnDiscardPile());
      skyjo.on(new PlayerFlipsCardDuringGame(Bob, Position.fromIndex(i)));
    }

    assertThat(skyjo.gameFinished()).isTrue();
  }

  @Test
  void game_is_over_but_one_player_has_many_unflipped_cards_left() {
    Deck deck = new Deck();
    Skyjo skyjo = new Skyjo(deck);
    Player Alice = new Player("Alice");
    Player Bob = new Player("Bob");
    skyjo.registerPlayer(Alice);
    skyjo.registerPlayer(Bob);

    var storyboard = new StoryBoard();
    skyjo.deal();
    storyboard.add(skyjo);

    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();

    for (int i = 2; i < 12; i++) {
      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerPutsCardOnDiscardPile());
      skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.fromIndex(i)));

      skyjo.on(new PlayerTakesCardFromDeck());
      skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(Position.fromIndex(1)));
    }
    storyboard.add(skyjo);

    Approvals.verify(storyboard);
    assertThat(skyjo.gameFinished()).isTrue();
  }
}
