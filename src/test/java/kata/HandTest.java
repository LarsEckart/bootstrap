package kata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HandTest {

  @Test
  void empty_hand_has_zero_size() {
    var hand = new Hand();

    assertThat(hand.size()).isZero();
    assertThat(hand.getScore().getValue()).isZero();
  }

  @Test
  void hand_can_be_created_with_cards() {
    var cards = List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.KING));
    var hand = new Hand(cards);

    assertThat(hand.size()).isEqualTo(2);
    assertThat(hand.getScore().getValue()).isEqualTo(21);
  }

  @Test
  void can_add_card_to_hand() {
    var hand = new Hand();

    hand.addCard(new Card(Suit.HEARTS, Rank.FIVE));

    assertThat(hand.size()).isEqualTo(1);
    assertThat(hand.getScore().getValue()).isEqualTo(5);
  }

  @Nested
  class multi_card_hands {

    @Test
    void three_card_hand_calculates_score() {
      var hand = new Hand();
      hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
      hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));
      hand.addCard(new Card(Suit.CLUBS, Rank.SEVEN));

      assertThat(hand.size()).isEqualTo(3);
      assertThat(hand.getScore().getValue()).isEqualTo(21);
      assertThat(hand.getScore().isBlackjack(3)).isFalse();
    }

    @Test
    void four_card_hand_with_aces() {
      var hand = new Hand();
      hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
      hand.addCard(new Card(Suit.SPADES, Rank.ACE));
      hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
      hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));

      assertThat(hand.size()).isEqualTo(4);
      assertThat(hand.getScore().getValue()).isEqualTo(21);
      assertThat(hand.getScore().isSoft()).isTrue();
    }

    @Test
    void five_card_hand_under_21() {
      var hand = new Hand();
      hand.addCard(new Card(Suit.HEARTS, Rank.TWO));
      hand.addCard(new Card(Suit.SPADES, Rank.THREE));
      hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
      hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));
      hand.addCard(new Card(Suit.HEARTS, Rank.SIX));

      assertThat(hand.size()).isEqualTo(5);
      assertThat(hand.getScore().getValue()).isEqualTo(20);
      assertThat(hand.getScore().isBust()).isFalse();
    }

    @Test
    void six_card_hand_that_busts() {
      var hand = new Hand();
      hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
      hand.addCard(new Card(Suit.SPADES, Rank.FIVE));
      hand.addCard(new Card(Suit.CLUBS, Rank.THREE));
      hand.addCard(new Card(Suit.DIAMONDS, Rank.TWO));
      hand.addCard(new Card(Suit.HEARTS, Rank.TWO));
      hand.addCard(new Card(Suit.SPADES, Rank.TWO));

      assertThat(hand.size()).isEqualTo(6);
      assertThat(hand.getScore().getValue()).isEqualTo(24);
      assertThat(hand.getScore().isBust()).isTrue();
    }
  }

  @Nested
  class hand_comparison {

    @Test
    void higher_score_beats_lower_score() {
      var hand1 =
          new Hand(List.of(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.SPADES, Rank.NINE)));
      var hand2 =
          new Hand(List.of(new Card(Suit.CLUBS, Rank.TEN), new Card(Suit.DIAMONDS, Rank.EIGHT)));

      assertThat(hand1.beats(hand2)).isTrue();
      assertThat(hand2.beats(hand1)).isFalse();
    }

    @Test
    void non_bust_beats_bust() {
      var nonBustHand =
          new Hand(List.of(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.SPADES, Rank.NINE)));
      var bustHand =
          new Hand(
              List.of(
                  new Card(Suit.CLUBS, Rank.TEN),
                  new Card(Suit.DIAMONDS, Rank.TEN),
                  new Card(Suit.HEARTS, Rank.FIVE)));

      assertThat(nonBustHand.beats(bustHand)).isTrue();
      assertThat(bustHand.beats(nonBustHand)).isFalse();
    }

    @Test
    void blackjack_beats_regular_21() {
      var blackjackHand =
          new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.KING)));
      var regular21Hand =
          new Hand(
              List.of(
                  new Card(Suit.CLUBS, Rank.SEVEN),
                  new Card(Suit.DIAMONDS, Rank.SEVEN),
                  new Card(Suit.HEARTS, Rank.SEVEN)));

      assertThat(blackjackHand.beats(regular21Hand)).isTrue();
      assertThat(regular21Hand.beats(blackjackHand)).isFalse();
    }

    @Test
    void both_bust_hands_neither_wins() {
      var bustHand1 =
          new Hand(
              List.of(
                  new Card(Suit.HEARTS, Rank.TEN),
                  new Card(Suit.SPADES, Rank.TEN),
                  new Card(Suit.CLUBS, Rank.FIVE)));
      var bustHand2 =
          new Hand(
              List.of(
                  new Card(Suit.DIAMONDS, Rank.KING),
                  new Card(Suit.HEARTS, Rank.QUEEN),
                  new Card(Suit.SPADES, Rank.JACK)));

      assertThat(bustHand1.beats(bustHand2)).isFalse();
      assertThat(bustHand2.beats(bustHand1)).isFalse();
    }

    @Test
    void equal_scores_neither_hand_wins() {
      var threeCardHand =
          new Hand(
              List.of(
                  new Card(Suit.HEARTS, Rank.SEVEN),
                  new Card(Suit.SPADES, Rank.SEVEN),
                  new Card(Suit.CLUBS, Rank.SIX)));
      var twoCardHand =
          new Hand(List.of(new Card(Suit.DIAMONDS, Rank.TEN), new Card(Suit.HEARTS, Rank.TEN)));

      assertThat(threeCardHand.beats(twoCardHand)).isFalse();
      assertThat(twoCardHand.beats(threeCardHand)).isFalse();
    }
  }
}
