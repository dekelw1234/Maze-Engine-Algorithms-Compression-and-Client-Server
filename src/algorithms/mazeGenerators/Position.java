package algorithms.mazeGenerators;


import java.io.Serializable;

/**
 * Represents a position (single cell) within a maze, defined by its row and column indices.
 */
public class Position implements Serializable {
    private int row;
    private int column;

    /**
     * Constructs a new Position with specified row and column.
     *
     * @param row    The row index of the position.
     * @param column The column index of the position.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row index of this position.
     *
     * @return The row index.
     */
    public int getRowIndex() {
        return row;
    }
    /**
     * Returns the column index of this position.
     *
     * @return The column index.
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * Sets the row index of this position.
     *
     * @param row The new row index.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the column index of this position.
     *
     * @param column The new column index.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * returns a human-readable string representation of the position.
     *
     * @return A string in the format "(row, column)".
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    /**
     * Checks if this position is equal to another object.
     * Two positions are equal if their row and column values are the same.
     *
     * @param obj The object to compare with.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // are same object?
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }
}
