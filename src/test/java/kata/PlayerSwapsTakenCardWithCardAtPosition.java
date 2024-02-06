package kata;

import kata.position.Position;

final record PlayerSwapsTakenCardWithCardAtPosition(Position position) implements Event {}
