package algorithms.mazeGenerators;

/**
 * Represents a maze with a start and goal position.
 * Each cell in the maze can be either a wall (1) or a path (0).
 */
public class Maze {
    private int[][] maze;
    private int rows;
    private int columns;
    private Position startPosition;
    private Position goalPosition;

    /**
     * constructs a new Maze with specified dimensions.
     *
     * @param rows    Number of rows in the maze.
     * @param columns Number of columns in the maze.
     */
    public Maze(int rows, int columns) {

        if (rows<=1 && columns<=1){
            throw new IllegalArgumentException("Rows or columns number are not valid");
        }
        this.maze = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * returns the internal 2D array representing the maze.
     *
     * @return A 2D array where 0 represents a path and 1 represents a wall.
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * sets the start position of the maze.
     *
     * @param startPosition The position where the maze starts.
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * returns the start position of the maze.
     *
     * @return The starting position.
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * sets the end (goal) position of the maze.
     *
     * @param goalPosition The position where the maze ends.
     */
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * returns the end (goal) position of the maze.
     *
     * @return The goal position.
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * prints a visual representation of the maze.
     * 'S' indicates the start position, 'E' the goal, '0' a path, and '1' a wall.
     */
    public void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // if a start point
                if (new Position(i, j).equals(startPosition)) {
                    sb.append("S");
                }
                // אם מדובר בנקודת סיום
                else if (new Position(i, j).equals(goalPosition)) {
                    sb.append("E");  // End
                }
                // path =0
                else if (maze[i][j] == 0) {
                    sb.append("0");
                }

                // wall =1
                else {
                    sb.append("1");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }


}
