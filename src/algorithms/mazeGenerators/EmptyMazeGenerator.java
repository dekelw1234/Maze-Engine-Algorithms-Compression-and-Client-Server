package algorithms.mazeGenerators;

/**
 * A maze generator that creates an empty maze with no walls.
 */
public  class EmptyMazeGenerator extends AMazeGenerator{

    /**
     * Generates an empty maze where all cells are open (value 0).
     *      *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return A Maze object representing the empty maze.
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        //fill the empty maze with 0
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                maze.getMaze()[row][col] = 0;
            }
        }
        //set start and end pos
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(rows - 1, columns - 1));

        return maze;
    }
}
