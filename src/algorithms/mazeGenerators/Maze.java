package algorithms.mazeGenerators;

public class Maze {
    private int[][] maze;
    private int rows;
    private int columns;
    private Position startPosition;
    private Position goalPosition;


    public Maze(int rows, int columns) {
        this.maze = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    // Getter למערך המייצג את המבוך
    public int[][] getMaze() {
        return maze;
    }

    // Setter לקביעת נקודת התחלה
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    // Getter לנקודת התחלה, אם נדרש
    public Position getStartPosition() {
        return startPosition;
    }

    // Setter לקביעת נקודת סיום
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    // Getter לנקודת סיום, אם נדרש
    public Position getGoalPosition() {
        return goalPosition;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // נניח: 1 = קיר, 0 = דרך
                sb.append(maze[i][j] == 1 ? "█" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
