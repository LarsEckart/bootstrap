package kata;

import kata.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PlayingCardsTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void three_cards_in_horizontal_row_are_removed_and_thus_dont_count(int column) {
        Card card1 = Card.faceUp(Points.of(1));
        Card card2 = Card.faceUp(Points.of(1));
        Card card3 = Card.faceUp(Points.of(1));

        Deck deck = new Deck();
        PlayingCards playingCards = new PlayingCards();

        for (int i = 0; i < 12; i++) {
            playingCards.addCard(deck.dealFromTop());
        }

        playingCards.swap(Position.atRow(1).atColumn(column), card1);
        playingCards.swap(Position.atRow(2).atColumn(column), card2);
        playingCards.swap(Position.atRow(3).atColumn(column), card3);

        assertThat(playingCards.score()).isEqualTo(0);
    }

    // SMELL: this test depends on the Deck not being shuffled
    @Test
    void flipping_causing_3_in_a_vertical_row_also_excludes_them() {

        Deck deck = new Deck();
        PlayingCards playingCards = new PlayingCards();

        for (int i = 0; i < 12; i++) {
            playingCards.addCard(deck.dealFromTop());
        }

        playingCards.flipCard(Position.atRow(1).atColumn(1));
        playingCards.flipCard(Position.atRow(2).atColumn(1));
        playingCards.flipCard(Position.atRow(3).atColumn(1));


        assertThat(playingCards.score()).isEqualTo(0);
    }

    // SMELL: this test depends on the Deck not being shuffled
    @Test
    void three_cards_in_horizontal_row_are_removed_and_returned_to_player_to_put_on_discard_pile_when_flipped() {
        Card card1 = Card.faceUp(Points.of(1));
        Card card2 = Card.faceUp(Points.of(1));
        Card card3 = Card.faceUp(Points.of(1));

        Deck deck = new Deck();
        PlayingCards playingCards = new PlayingCards();

        for (int i = 0; i < 12; i++) {
            playingCards.addCard(deck.dealFromTop());
        }

        playingCards.flipCard(Position.atRow(1).atColumn(1));
        playingCards.flipCard(Position.atRow(2).atColumn(1));
        Optional<Card> card = playingCards.flipCard(Position.atRow(3).atColumn(1));

        assertThat(card.get().value()).isEqualTo(12);
    }

    // SMELL: this test depends on the Deck not being shuffled
    @Test
    void three_cards_in_horizontal_row_are_removed_and_returned_to_player_to_put_on_discard_pile_when_swapped() {
        Card card1 = Card.faceUp(Points.of(1));
        Card card2 = Card.faceUp(Points.of(1));
        Card card3 = Card.faceUp(Points.of(1));

        Deck deck = new Deck();
        PlayingCards playingCards = new PlayingCards();

        for (int i = 0; i < 12; i++) {
            playingCards.addCard(deck.dealFromTop());
        }

        playingCards.swap(Position.atRow(1).atColumn(1), card1);
        playingCards.swap(Position.atRow(2).atColumn(1), card2);
        Card card = playingCards.swap(Position.atRow(3).atColumn(1), card3);

        assertThat(card.value()).isEqualTo(1);
    }

    @Test
    void all_cards_flipped_when_all_vertical_rows_completed_and_removed() {
        PlayingCards playingCards = setupPlayingCardsWithAllCardsTheSame();

        flipAllCards(playingCards);

        assertThat(playingCards.allCardsFlipped()).isTrue();
    }

    private static PlayingCards setupPlayingCardsWithAllCardsTheSame() {
        PlayingCards playingCards = new PlayingCards();

        for (int i = 0; i < 12; i++) {
            playingCards.addCard(Card.of(Points.of(1)));
        }
        return playingCards;
    }

    private static void flipAllCards(PlayingCards playingCards) {
        for (int i = 0; i < 12; i++) {
            playingCards.flipCard(Position.fromIndex(i));
        }
    }
}
