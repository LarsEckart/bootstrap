package kata.position;

public class PositionWithRow {
    private final int row;

    PositionWithRow(int row) {
        this.row = row;
    }

    public Position atColumn(int column) {
        return new Position(this.row, column);
    }
}
