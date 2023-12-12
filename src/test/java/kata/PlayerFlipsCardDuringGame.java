package kata;

import kata.position.Position;

/**
 * @deprecated use with Position instead
 *
 */
@Deprecated
final record PlayerFlipsCardDuringGame(Player player, int row, int column) implements Event {
    public boolean isValidMove() {
        return !this.player().cardAlreadyFlipped(Position.atRow(this.row()).atColumn(this.column()));
    }
}
