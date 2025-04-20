package algorithms.mazeGenerators;

/**
 * An interface for classes that generate mazes.
 */
public interface IMazeGenerator {

    /**
     * Generates a new maze with the specified number of rows and columns.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return A Maze object representing the generated maze.
     */
    Maze generate(int rows, int columns);

    /**
     * Measures the time (in milliseconds) it takes to generate a maze with the given dimensions.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return The time in milliseconds it took to generate the maze.
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
