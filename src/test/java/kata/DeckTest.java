package kata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void taking_all_150_cards_yields_10_with_value_12() {
        Deck deck = new Deck();

        int counterOfTwelves = 0;
        for (int i = 0; i < 150; i++) {
            if (deck.takeFromTop().value() == 12) {
                counterOfTwelves++;
            }
        }

        assertEquals(10, counterOfTwelves);
    }

    @Test
    void taking_all_150_cards_yields_10_with_value_11() {
        Deck deck = new Deck();

        int counterOfElevens = 0;
        for (int i = 0; i < 150; i++) {
            if (deck.takeFromTop().value() == 11) {
                counterOfElevens++;
            }
        }

        assertEquals(10, counterOfElevens);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
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
}
