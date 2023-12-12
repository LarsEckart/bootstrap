package kata.position;

public class PositionWithColumn {

    private final int column;

    PositionWithColumn(int column) {
        this.column = column;
    }

    public Position atRow(int row) {
        return new Position(row, this.column);
    }
}
