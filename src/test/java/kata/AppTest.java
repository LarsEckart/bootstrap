package kata;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {

    @Test
    void deals_12_cards_to_each_player() {
        Deck deck = new Deck();
        Skyjo skyjo = new Skyjo(deck);
        Player Alice = new Player();
        Player Bob = new Player();
        skyjo.registerPlayer(Alice);
        skyjo.registerPlayer(Bob);

        skyjo.deal();

        assertThat(Alice.numberOfCards()).isEqualTo(12);
        assertThat(Bob.numberOfCards()).isEqualTo(12);
    }
}
