package kata.position;

import java.util.Objects;

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

    public int toIndex() {
      return (getRow() - 1) * 3 + getColumn();
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
