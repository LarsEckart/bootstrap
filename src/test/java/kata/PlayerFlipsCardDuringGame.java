package kata;

import kata.position.Position;

final record PlayerFlipsCardDuringGame(Player player, Position position) implements Event {
    public boolean isValidMove() {
        return !this.player().cardAlreadyFlipped(position);
    }
}
