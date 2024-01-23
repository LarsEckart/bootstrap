package kata;

import kata.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayingCardsTest {

    @Test
    void three_cards_in_horizontal_row_are_removed_and_thus_dont_count() {
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
        playingCards.swap(Position.atRow(3).atColumn(1), card3);

        assertThat(playingCards.score()).isEqualTo(0);
    }
}
