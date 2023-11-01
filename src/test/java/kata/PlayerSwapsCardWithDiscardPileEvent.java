package kata;

final record PlayerSwapsCardWithDiscardPileEvent(int row, int column) implements Event {}
