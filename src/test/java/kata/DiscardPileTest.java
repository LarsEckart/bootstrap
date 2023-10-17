package kata;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestCommitRevertMainExtension.class)
class DiscardPileTest {

    @Test
    void apple_sauce() {
        DiscardPile discardPile = new DiscardPile(new Card(Points.of(5)));

        Card card = discardPile.takeFromTop();

        assertThat(card).isEqualTo(new Card(Points.of(5)));
    }
}
