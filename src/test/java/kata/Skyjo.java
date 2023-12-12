package kata;

import kata.position.Position;

import java.util.ArrayList;
import java.util.List;

class Skyjo {
    private final Deck deck;
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private DiscardPile discardPile;

    public Skyjo(Deck deck) {
        this.deck = deck;
    }

    public void deal() {
        for (Player player : players) {
            for (int i = 0; i < 12; i++) {
                player.addCard(this.deck.dealFromTop());
            }
        }

        discardPile = new DiscardPile(this.deck.takeFromTop());
    }

    public void registerPlayer(Player alice) {
        this.players.add(alice);
    }

    public Player playerWithHighestScore() {
        Player highest = players.get(0);
        for (Player player : players) {
            if (player.score() > highest.score()) {
                highest = player;
            }
        }

        return highest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Player player : players) {
            sb.append(player.toString());
            sb.append("\n");
        }

        sb.append("Discard pile: " + discardPile.toString());
        sb.append("\n");

        if (currentPlayer != null) {
            sb.append("Current player: " + currentPlayer.name());
            sb.append("\n");
        }

        return sb.toString();
    }

    public void on(Event event) {
        switch (event) {
            case PlayerTakesCardFromDeck e -> onPlayerTakesCardFromDeck(e);
            case PlayerSwapsTakenCardWithCardAtPosition e -> onPlayerSwapsTakenCardWithCardAtPosition(e);
            case PlayerFlipsCard e -> onPlayerFlipsCard(e);
            case PlayerTakesCardFromDiscardPile e -> onPlayerTakesCardFromDiscardPile(e);
            case PlayerPutsCardOnDiscardPile e -> onPlayerPutsCardOnDiscardPile(e);
            case PlayerFlipsCardDuringGame e -> onPlayerFlipsCardDuringGame(e);
        }
    }

    private void onPlayerFlipsCardDuringGame(PlayerFlipsCardDuringGame e) {
        e.player().flipCard(Position.atRow(e.row()).atColumn(e.column()));

        currentPlayer = determineNextPlayer();
    }

    private void onPlayerPutsCardOnDiscardPile(PlayerPutsCardOnDiscardPile e) {
        Card card = currentPlayer.getPendingCard();

        discardPile = new DiscardPile(card);
    }

    private void onPlayerTakesCardFromDiscardPile(PlayerTakesCardFromDiscardPile event) {
        var card = this.discardPile.takeFromTop();
        currentPlayer.acceptIncomingCard(card);
    }

    private void onPlayerFlipsCard(PlayerFlipsCard event) {
        event.player().flipCard(Position.atRow(event.row()).atColumn(event.column()));
    }

    private void onPlayerSwapsTakenCardWithCardAtPosition(PlayerSwapsTakenCardWithCardAtPosition event) {
        var card = currentPlayer.swap(Position.atRow(event.row()).atColumn(event.column()));
        card.flip();
        this.discardPile = new DiscardPile(card);
        currentPlayer = determineNextPlayer();
    }

    private Player determineNextPlayer() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void onPlayerTakesCardFromDeck(PlayerTakesCardFromDeck event) {
        var card = this.deck.takeFromTop();

        currentPlayer.acceptIncomingCard(card);
    }

    public void start() {
        currentPlayer = playerWithHighestScore();
    }
}
