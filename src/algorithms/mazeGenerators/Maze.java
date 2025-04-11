package algorithms.mazeGenerators;

public class Maze {
    private int[][] maze;
    private int rows;
    private int columns;
    private int[][] map;
    private Position startPosition;
    private Position goalPosition;

    public Maze(int rows, int columns) {
        this.maze = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

}
