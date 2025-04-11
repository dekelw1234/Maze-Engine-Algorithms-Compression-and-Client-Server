package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;

    // בנאי שמקבל את השורה והעמודה
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // Getters לקבלת ערכים
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Setters לשינוי ערכים אם נדרש
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    // מתודה להדפסת המיקום בצורה קריאה
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    // הגדרת שוויון בין מיקומים – חשוב כאשר נשווה בין מיקומים
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // הם אותו מופע
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }
}
