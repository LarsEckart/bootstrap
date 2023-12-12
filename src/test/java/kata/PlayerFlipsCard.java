package kata;

@Deprecated
public record PlayerFlipsCard(Player player, int row, int column) implements Event {}
