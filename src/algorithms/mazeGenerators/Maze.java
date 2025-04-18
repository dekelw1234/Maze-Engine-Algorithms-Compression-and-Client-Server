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

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // אם מדובר בנקודת התחלה
                if (new Position(i, j).equals(startPosition)) {
                    sb.append("S");  // נקודת התחלה
                }
                // אם מדובר בנקודת סיום
                else if (new Position(i, j).equals(goalPosition)) {
                    sb.append("E");  // נקודת סיום
                }
                // אם זה דרך (ערך 0)
                else if (maze[i][j] == 0) {
                    sb.append("0");  // דרך (ריקה)
                }
                // אם זה קיר (ערך 1)
                else {
                    sb.append("1");  // קיר
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }


}
