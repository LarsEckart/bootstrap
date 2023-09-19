package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    @Test
    void deck_contains_150_cards() {
        Deck deck = new Deck();

        assertEquals(150, deck.numberOfCards());
    }

    @Test
    void size_after_taking_from_top_of_deck_is_149() {
        Deck deck = new Deck();

        deck.takeFromTop();

        assertEquals(149, deck.numberOfCards());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void taking_all_150_cards_yields_10_with_value(int value) {
        Deck deck = new Deck();

        int counter = 0;
        for (int i = 0; i < 150; i++) {
            if (deck.takeFromTop().value() == value) {
                counter++;
            }
        }

        assertEquals(10, counter);
    }

    @Test
    void taking_all_150_cards_yields_15_cards_with_value_0() {
        Deck deck = new Deck();

        int counter = 0;
        for (int i = 0; i < 150; i++) {
            if (deck.takeFromTop().value() == 0) {
                counter++;
            }
        }

        assertEquals(15, counter);
    }

    @Test
    void taking_all_150_cards_yields_5_cards_with_value_minus_2() {
        Deck deck = new Deck();

        int counter = 0;
        for (int i = 0; i < 150; i++) {
            if (deck.takeFromTop().value() == -2) {
                counter++;
            }
        }

        assertEquals(5, counter);
    }
}
