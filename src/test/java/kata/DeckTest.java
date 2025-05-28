package kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DeckTest {

  @Test
  void deck_contains_52_cards() {
    var deck = new Deck();

    assertThat(deck.size()).isEqualTo(52);
  }

  @Test
  void deck_contains_all_suits_and_ranks() {
    var deck = new Deck();
    var cards = deck.getCards();

    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        var expectedCard = new Card(suit, rank);
        assertThat(cards).contains(expectedCard);
      }
    }
  }

  @Test
  void deck_contains_exactly_one_of_each_card() {
    var deck = new Deck();
    var cards = deck.getCards();

    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        var expectedCard = new Card(suit, rank);
        var count = cards.stream().mapToInt(card -> card.equals(expectedCard) ? 1 : 0).sum();
        assertThat(count).isEqualTo(1);
      }
    }
  }

  @Nested
  class suit_verification {

    @Test
    void deck_contains_13_hearts() {
      var deck = new Deck();
      var hearts = deck.getCards().stream().filter(card -> card.getSuit() == Suit.HEARTS).toList();

      assertThat(hearts).hasSize(13);
    }

    @Test
    void deck_contains_13_diamonds() {
      var deck = new Deck();
      var diamonds =
          deck.getCards().stream().filter(card -> card.getSuit() == Suit.DIAMONDS).toList();

      assertThat(diamonds).hasSize(13);
    }

    @Test
    void deck_contains_13_clubs() {
      var deck = new Deck();
      var clubs = deck.getCards().stream().filter(card -> card.getSuit() == Suit.CLUBS).toList();

      assertThat(clubs).hasSize(13);
    }

    @Test
    void deck_contains_13_spades() {
      var deck = new Deck();
      var spades = deck.getCards().stream().filter(card -> card.getSuit() == Suit.SPADES).toList();

      assertThat(spades).hasSize(13);
    }
  }

  @Nested
  class rank_verification {

    @Test
    void deck_contains_4_aces() {
      var deck = new Deck();
      var aces = deck.getCards().stream().filter(card -> card.getRank() == Rank.ACE).toList();

      assertThat(aces).hasSize(4);
    }

    @Test
    void deck_contains_4_kings() {
      var deck = new Deck();
      var kings = deck.getCards().stream().filter(card -> card.getRank() == Rank.KING).toList();

      assertThat(kings).hasSize(4);
    }
  }

  @Nested
  class shuffle_tests {

    @Test
    void shuffle_preserves_all_cards() {
      var deck = new Deck();
      var originalCards = deck.getCards();

      deck.shuffle();
      var shuffledCards = deck.getCards();

      assertThat(shuffledCards).hasSize(52);
      assertThat(shuffledCards).containsExactlyInAnyOrderElementsOf(originalCards);
    }

    @Test
    void shuffle_changes_card_order() {
      var deck = new Deck();
      var originalCards = deck.getCards();

      deck.shuffle();
      var shuffledCards = deck.getCards();

      var orderChanged = false;
      for (int i = 0; i < originalCards.size(); i++) {
        if (!originalCards.get(i).equals(shuffledCards.get(i))) {
          orderChanged = true;
          break;
        }
      }

      assertThat(orderChanged).isTrue();
    }

    @Test
    void multiple_shuffles_produce_different_orders() {
      var deck = new Deck();
      deck.shuffle();
      var firstShuffle = deck.getCards();

      deck.shuffle();
      var secondShuffle = deck.getCards();

      var ordersDifferent = false;
      for (int i = 0; i < firstShuffle.size(); i++) {
        if (!firstShuffle.get(i).equals(secondShuffle.get(i))) {
          ordersDifferent = true;
          break;
        }
      }

      assertThat(ordersDifferent).isTrue();
    }
  }

  @Nested
  class dealing_tests {

    @Test
    void deal_returns_card_from_deck() {
      var deck = new Deck();
      var originalSize = deck.size();

      var dealtCard = deck.deal();

      assertThat(dealtCard).isPresent();
      assertThat(deck.size()).isEqualTo(originalSize - 1);
    }

    @Test
    void deal_removes_card_from_deck() {
      var deck = new Deck();
      var originalCards = deck.getCards();

      var dealtCard = deck.deal();

      assertThat(dealtCard).isPresent();
      var remainingCards = deck.getCards();
      assertThat(remainingCards).doesNotContain(dealtCard.get());
      assertThat(remainingCards.size()).isEqualTo(originalCards.size() - 1);
    }

    @Test
    void deal_from_empty_deck_returns_empty() {
      var deck = new Deck();

      for (int i = 0; i < 52; i++) {
        deck.deal();
      }

      var result = deck.deal();

      assertThat(result).isEmpty();
      assertThat(deck.size()).isZero();
    }
  }
}
