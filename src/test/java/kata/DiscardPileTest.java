package kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DiscardPileTest {

  @Test
  void card_can_be_taken() {
    var discardPile = new DiscardPile(Card.of(Points.of(5)));

    var card = discardPile.takeFromTop();

    assertThat(card).isEqualTo(Card.of(Points.of(5)));
  }
}
