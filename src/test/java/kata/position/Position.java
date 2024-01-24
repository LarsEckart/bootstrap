package kata.position;

import java.util.Objects;
import java.util.Set;

public final class Position {
    private final int row;
    private final int column;

    Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static PositionWithRow atRow(int row) {
        return new PositionWithRow(row);
    }

    public static PositionWithColumn atColumn(int column) {
        return new PositionWithColumn(column);
    }

    public static Set<Position> allInVerticalRow(int verticalRow) {
        return Set.of(
                Position.atRow(1).atColumn(verticalRow),
                Position.atRow(2).atColumn(verticalRow),
                Position.atRow(3).atColumn(verticalRow));
    }

    public int toIndex() {
        return switch (this.row) {
            case 1 -> switch (this.column) {
                case 1 -> 0;
                case 2 -> 1;
                case 3 -> 2;
                case 4 -> 3;
                default -> throw new IllegalArgumentException("Invalid column: " + this.column);
            };
            case 2 -> switch (this.column) {
                case 1 -> 4;
                case 2 -> 5;
                case 3 -> 6;
                case 4 -> 7;
                default -> throw new IllegalArgumentException("Invalid column: " + this.column);
            };
            case 3 -> switch (this.column) {
                case 1 -> 8;
                case 2 -> 9;
                case 3 -> 10;
                case 4 -> 11;
                default -> throw new IllegalArgumentException("Invalid column: " + this.column);
            };
            default -> throw new IllegalArgumentException("Invalid row: " + this.row);
        };
    }

    public static Position fromIndex(int index) {
        return switch (index) {
            default -> throw new IllegalArgumentException("Invalid index: " + index);
            case 0 -> new Position(1, 1);
            case 1 -> new Position(1, 2);
            case 2 -> new Position(1, 3);
            case 3 -> new Position(1, 4);
            case 4 -> new Position(2, 1);
            case 5 -> new Position(2, 2);
            case 6 -> new Position(2, 3);
            case 7 -> new Position(2, 4);
            case 8 -> new Position(3, 1);
            case 9 -> new Position(3, 2);
            case 10 -> new Position(3, 3);
            case 11 -> new Position(3, 4);
        };
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Position) obj;
        return this.row == that.row &&
               this.column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position[" +
               "row=" + row + ", " +
               "column=" + column + ']';
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
