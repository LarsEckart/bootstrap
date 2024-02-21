package kata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Skyjo {
    private final Deck deck;
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private DiscardPile discardPile;
    private boolean lastRound;

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

    public void registerPlayer(Player player) {
        this.players.add(player);
    }

    public Player playerWithHighestScore() {
        Player highest = players.getFirst();
        for (Player player : players) {
            if (player.score() > highest.score()) {
                highest = player;
            }
        }

        return highest;
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
        if (!e.isValidMove()) {
            throw new IllegalStateException("Cannot flip card that is already flipped.");
        }

        Optional<Card> card = e.player().flipCard(e.position());

        card.ifPresent(c -> this.discardPile = new DiscardPile(c));

        if (!lastRound) {
            this.lastRound = e.player().isFinishedPlaying();
        }


        currentPlayer = determineNextPlayer(players, currentPlayer);
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
        event.player().flipCard(event.position());
    }

    private void onPlayerSwapsTakenCardWithCardAtPosition(PlayerSwapsTakenCardWithCardAtPosition event) {
        Card card = lastRound ? currentPlayer.swapOneLastTime(event.position()) : currentPlayer.swap(event.position());

        if (currentPlayer.isFinishedPlaying()) {
            this.lastRound = true;
        }

        card.flip();
        this.discardPile = new DiscardPile(card);
        currentPlayer = determineNextPlayer(players, this.currentPlayer);

    }

    private static Player determineNextPlayer(List<Player> players, Player currentPlayer) {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void onPlayerTakesCardFromDeck(PlayerTakesCardFromDeck event) {
        var card = this.deck.takeFromTop();

        currentPlayer.acceptIncomingCard(card);
    }

    public void start() {
        currentPlayer = playerWithHighestScore();
    }

    public boolean gameFinished() {
        return players.stream().filter(Player::isFinishedPlaying).allMatch(Player::playedLastTurn);
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
}
