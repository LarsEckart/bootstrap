package kata;

final record PlayerFlipsCardDuringGame(Player player, int row, int column) implements Event {
}
