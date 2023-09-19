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

    @Test
    void after_flipping_one_card_player_has_a_score_of_twelve() {
        Player player = new Player();
        Deck deck = new Deck();
        for (int i = 0; i < 12; i++) {
            player.addCard(deck.takeFromTop());
        }

        player.flipCard(1, 1);

        assertEquals(12, player.score());
    }

    @Test
    void after_flipping_two_cards_the_score_is_twenty_four() {
        Player player = new Player();
        Deck deck = new Deck();
        for (int i = 0; i < 12; i++) {
            player.addCard(deck.takeFromTop());
        }

        player.flipCard(1, 1);
        player.flipCard(1, 2);

        assertEquals(24, player.score());
    }
}
