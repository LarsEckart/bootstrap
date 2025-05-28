package kata;

import java.util.Scanner;

public class BlackjackGame {
  private Deck deck;
  private Hand playerHand;
  private Hand dealerHand;

  public BlackjackGame() {
    this.deck = new Deck();
    this.playerHand = new Hand();
    this.dealerHand = new Hand();
  }

  public void playRound(Scanner scanner) {
    setupNewRound();
    dealInitialCards();
    displayInitialHands();

    if (playerHand.getScore().isBlackjack(2)) {
      if (dealerHand.getScore().isBlackjack(2)) {
        System.out.println("Both have blackjack! It's a tie!");
      } else {
        System.out.println("Blackjack! You win!");
      }
      return;
    }

    playerTurn(scanner);

    if (!playerHand.getScore().isBust()) {
      dealerTurn();
    }

    determineWinner();
  }

  private void setupNewRound() {
    deck = new Deck();
    deck.shuffle();
    playerHand = new Hand();
    dealerHand = new Hand();
  }

  private void dealInitialCards() {
    playerHand.addCard(deck.deal().orElseThrow());
    dealerHand.addCard(deck.deal().orElseThrow());
    playerHand.addCard(deck.deal().orElseThrow());
    dealerHand.addCard(deck.deal().orElseThrow());
  }

  private void displayInitialHands() {
    System.out.println("\n--- Initial Deal ---");
    System.out.println("Your hand: " + playerHand);
    System.out.println("Dealer hand: [Hidden Card], [Up Card]");
  }

  private void playerTurn(Scanner scanner) {
    while (!playerHand.getScore().isBust()) {
      System.out.print("Hit or Stand? (h/s): ");
      var action = scanner.nextLine().trim().toLowerCase();

      if (action.equals("h") || action.equals("hit")) {
        playerHand.addCard(deck.deal().orElseThrow());
        System.out.println("You drew a card.");
        System.out.println("Your hand: " + playerHand);

        if (playerHand.getScore().isBust()) {
          System.out.println("Bust! You lose!");
          return;
        }
      } else if (action.equals("s") || action.equals("stand")) {
        System.out.println("You stand with " + playerHand.getScore());
        break;
      } else {
        System.out.println("Please enter 'h' for hit or 's' for stand.");
      }
    }
  }

  private void dealerTurn() {
    System.out.println("\n--- Dealer's Turn ---");
    System.out.println("Dealer reveals: " + dealerHand);

    while (dealerHand.getScore().getValue() < 17
        || (dealerHand.getScore().getValue() == 17 && dealerHand.getScore().isSoft())) {
      dealerHand.addCard(deck.deal().orElseThrow());
      System.out.println("Dealer hits: " + dealerHand);
    }

    if (dealerHand.getScore().isBust()) {
      System.out.println("Dealer busts! You win!");
    } else {
      System.out.println("Dealer stands with " + dealerHand.getScore());
    }
  }

  private void determineWinner() {
    if (playerHand.getScore().isBust()) {
      return;
    }

    if (dealerHand.getScore().isBust()) {
      return;
    }

    System.out.println("\n--- Final Results ---");
    System.out.println("Your hand: " + playerHand);
    System.out.println("Dealer hand: " + dealerHand);

    if (playerHand.beats(dealerHand)) {
      System.out.println("You win!");
    } else if (dealerHand.beats(playerHand)) {
      System.out.println("Dealer wins!");
    } else {
      System.out.println("It's a tie!");
    }
  }
}
