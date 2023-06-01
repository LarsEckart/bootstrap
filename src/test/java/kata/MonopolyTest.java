package kata;

import com.github.larseckart.tcr.FastTestCommitRevertMainExtension;
import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(FastTestCommitRevertMainExtension.class)
class MonopolyTest {

    @Test
    void testBasicPlay() {
        StoryBoard story = new StoryBoard();
        // create a board with 3 players
        Monopoly monopoly = new Monopoly(3);
        story.addFrame(monopoly.toString());
        Player player1 = monopoly.getPlayer(0);
        monopoly.move(5).buyProperty().endTurn();
        story.addFrame(monopoly.toString());
        // player 1 rolls 5, buys railroad and ends turn.
        // player 2 rolls 3, buys baltic avenue and ends turn.
        // player 3 rolls 3, pays rent and ends turn.
        Approvals.verify(story);
    }

    @Test
    void testPlayerDetails() {
        Player player = new Player("Player 1");

        assertThat(player.details()).isEqualTo("Player 1 ($1500) ");
    }

    @Test
    void testPlayerLocation() {
        Player player = new Player("Player 1");

        assertThat(player.location()).isEqualTo(0);
    }

}
