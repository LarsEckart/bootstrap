package kata;

import com.github.larseckart.tcr.TestCommitRevertExtension;
import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestCommitRevertExtension.class)
class MonopolyTest {

    @Test
    void testBasicPlay() {
        StoryBoard story = new StoryBoard();
        Monopoly monopoly = new Monopoly(3);
        story.addFrame(monopoly);
        story.addFrame(monopoly.move(5).buyProperty().endTurn());
        story.addFrame(monopoly.move(3).buyProperty().endTurn());
        story.addFrame(monopoly.move(3).endTurn());
        Approvals.verify(story);
    }

    @Test
    void testIncomeTaxAndChanceAndJail() {
        StoryBoard story = new StoryBoard();
        Monopoly monopoly = new Monopoly(3);
        story.addFrame(monopoly);
        story.addFrame(monopoly.move(10).endTurn());
        story.addFrame(monopoly.move(4).endTurn());
        story.addFrame(monopoly.move(7).endTurn());
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
