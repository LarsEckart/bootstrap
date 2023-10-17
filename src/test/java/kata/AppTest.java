package kata;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(TestCommitRevertMainExtension.class)
@UseReporter(IntelliJReporter.class)
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
    void after_dealing_players_flip_two_cards_and_one_with_highest_score_starts_the_game() {
        // problem: test passes but from looking at test we dont understand why.
        Deck deck = new Deck();
        Skyjo skyjo = new Skyjo(deck);
        Player Alice = new Player("Alice");
        Player Bob = new Player("Bob");
        skyjo.registerPlayer(Alice);
        skyjo.registerPlayer(Bob);

        skyjo.deal();
        Alice.flipCard(1, 1);
        Alice.flipCard(1, 2);
        Bob.flipCard(1, 1);
        Bob.flipCard(1, 2);

        Approvals.verify(skyjo);
    }
}
