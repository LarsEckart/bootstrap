package kata;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestCommitRevertMainExtension.class)
class DiscardPileTest {

    @Test
    void card_can_be_taken() {
        var discardPile = new DiscardPile(new Card(Points.of(5)));

        var card = discardPile.takeFromTop();

        assertThat(card).isEqualTo(new Card(Points.of(5)));
    }
}
