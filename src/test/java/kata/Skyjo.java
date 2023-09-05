package kata;

class Skyjo {

    private Deck deck = new Deck();
    private Players players = new Players();
    private boolean gameStarted;

    public Skyjo(Player alice, Player bob) {
        players.add(alice);
        players.add(bob);
        deck.shuffle();
    }

    public void startGame() {
        for (Player player : players) {
            for (int i = 0; i < 12; i++) {
                player.dealInitialCards(deck.pop());
            }
        }
    }

    public void flipInitialCards() {
        for (Player player : players) {
            player.flip(1, 1);
            player.flip(1, 2);
        }
        gameStarted = true;
    }

    @Override
    public String toString() {
        String result = "Deck: " + deck.cardCount() + "\n" +
                        "Players:\n";
        for (Player player : players) {
            result += player.toString() + "\n";
        }
        if (gameStarted) {
            result += "next is " + currentPlayer().name() + "\n";
        }
        return result;
    }

    private Player currentPlayer() {
        return players.withHighestScore();
    }
}
