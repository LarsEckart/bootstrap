package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kata.position.Position;
import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
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

    skyjo.on(new PlayerFlipsCard(Alice, Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Alice, Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Bob, Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Bob, Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();

    assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> skyjo.on(new PlayerFlipsCardDuringGame(Alice, Position.atRow(1).atColumn(1))))
            .withMessage("Cannot flip card that is already flipped.");

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

    skyjo.on(new PlayerFlipsCard(Alice, Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Alice, Position.atRow(1).atColumn(2)));
    skyjo.on(new PlayerFlipsCard(Bob, Position.atRow(1).atColumn(1)));
    skyjo.on(new PlayerFlipsCard(Bob, Position.atRow(1).atColumn(2)));

    storyboard.add(skyjo);
    skyjo.start();
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDeck());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(1, 1));
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDeck());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(1, 3));
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDiscardPile());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsTakenCardWithCardAtPosition(1, 3));
    storyboard.add(skyjo);

    // Player takes card from deck, puts it on discard pile, and flips one of their own cards.
    skyjo.on(new PlayerTakesCardFromDeck());
    skyjo.on(new PlayerPutsCardOnDiscardPile());
    storyboard.add(skyjo);
    skyjo.on(new PlayerFlipsCardDuringGame(Bob, Position.atRow(1).atColumn(4)));
    storyboard.add(skyjo);

    Approvals.verify(storyboard);
  }
}