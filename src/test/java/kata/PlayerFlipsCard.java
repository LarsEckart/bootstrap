package kata;

public record PlayerFlipsCard(Player player, int row, int column) implements Event {}
