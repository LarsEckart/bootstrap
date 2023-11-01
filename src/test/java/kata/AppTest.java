package kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// @ExtendWith(TestCommitRevertMainExtension.class)
// @UseReporter(BeyondCompareMacReporter.class)
class AppTest {

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

    skyjo.on(new PlayerFlipsCard(Alice, 1, 1));
    skyjo.on(new PlayerFlipsCard(Alice, 1, 2));
    skyjo.on(new PlayerFlipsCard(Bob, 1, 1));
    skyjo.on(new PlayerFlipsCard(Bob, 1, 2));

    storyboard.add(skyjo);
    skyjo.start();
    storyboard.add(skyjo);
    skyjo.on(new PlayerTakesCardFromDeckEvent());
    storyboard.add(skyjo);
    skyjo.on(new PlayerSwapsCardWithDiscardPileEvent(1, 1));
    storyboard.add(skyjo);

    Approvals.verify(storyboard);
  }
}
