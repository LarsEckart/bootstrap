package kata;

import kata.position.Position;

public record PlayerFlipsCard(Player player, Position position) implements Event {
}
