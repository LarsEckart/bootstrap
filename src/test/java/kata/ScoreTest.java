package kata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScoreTest {

  @Test
  void single_number_card_score() {
    var cards = List.of(new Card(Suit.HEARTS, Rank.FIVE));

    var score = Score.calculate(cards);

    assertThat(score.getValue()).isEqualTo(5);
    assertThat(score.isSoft()).isFalse();
  }

  @Test
  void face_card_values() {
    var jack = Score.calculate(List.of(new Card(Suit.HEARTS, Rank.JACK)));
    var queen = Score.calculate(List.of(new Card(Suit.SPADES, Rank.QUEEN)));
    var king = Score.calculate(List.of(new Card(Suit.CLUBS, Rank.KING)));

    assertThat(jack.getValue()).isEqualTo(10);
    assertThat(queen.getValue()).isEqualTo(10);
    assertThat(king.getValue()).isEqualTo(10);
  }

  @Nested
  class ace_scoring {

    @Test
    void single_ace_is_soft_eleven() {
      var cards = List.of(new Card(Suit.HEARTS, Rank.ACE));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(11);
      assertThat(score.isSoft()).isTrue();
    }

    @Test
    void ace_and_ten_is_blackjack() {
      var cards = List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.TEN));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(21);
      assertThat(score.isSoft()).isTrue();
      assertThat(score.isBlackjack(2)).isTrue();
    }

    @Test
    void ace_and_face_card_is_blackjack() {
      var cards = List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.KING));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(21);
      assertThat(score.isSoft()).isTrue();
      assertThat(score.isBlackjack(2)).isTrue();
    }

    @Test
    void ace_converts_to_one_when_busting() {
      var cards =
          List.of(
              new Card(Suit.HEARTS, Rank.ACE),
              new Card(Suit.SPADES, Rank.NINE),
              new Card(Suit.CLUBS, Rank.FIVE));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(15);
      assertThat(score.isSoft()).isFalse();
    }

    @Test
    void multiple_aces_adjust_properly() {
      var cards =
          List.of(
              new Card(Suit.HEARTS, Rank.ACE),
              new Card(Suit.SPADES, Rank.ACE),
              new Card(Suit.CLUBS, Rank.NINE));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(21);
      assertThat(score.isSoft()).isTrue();
    }
  }

  @Nested
  class bust_detection {

    @Test
    void over_21_is_bust() {
      var cards =
          List.of(
              new Card(Suit.HEARTS, Rank.TEN),
              new Card(Suit.SPADES, Rank.EIGHT),
              new Card(Suit.CLUBS, Rank.FIVE));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(23);
      assertThat(score.isBust()).isTrue();
    }

    @Test
    void exactly_21_is_not_bust() {
      var cards =
          List.of(
              new Card(Suit.HEARTS, Rank.TEN),
              new Card(Suit.SPADES, Rank.SIX),
              new Card(Suit.CLUBS, Rank.FIVE));

      var score = Score.calculate(cards);

      assertThat(score.getValue()).isEqualTo(21);
      assertThat(score.isBust()).isFalse();
    }
  }

  @Test
  void blackjack_requires_exactly_two_cards() {
    var twoCardTwentyOne =
        List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.KING));
    var threeCardTwentyOne =
        List.of(
            new Card(Suit.HEARTS, Rank.SEVEN),
            new Card(Suit.SPADES, Rank.SEVEN),
            new Card(Suit.CLUBS, Rank.SEVEN));

    var twoCardScore = Score.calculate(twoCardTwentyOne);
    var threeCardScore = Score.calculate(threeCardTwentyOne);

    assertThat(twoCardScore.isBlackjack(2)).isTrue();
    assertThat(threeCardScore.isBlackjack(3)).isFalse();
  }
}
