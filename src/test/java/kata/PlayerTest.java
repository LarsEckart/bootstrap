package kata;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestCommitRevertMainExtension.class)
class PlayerTest {

    @Test
    void initially_player_has_score_zero() {
        Player player = new Player();
        Deck deck = new Deck();
        for (int i = 0; i < 12; i++) {
            player.addCard(deck.takeFromTop());
        }

        assertEquals(0, player.score());
    }
}
