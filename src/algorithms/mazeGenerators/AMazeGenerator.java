package algorithms.mazeGenerators;

/**
 * An abstract class that provides a base implementation for maze generators.
 * Implements the interface
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Generates a maze with the specified number of rows and columns.
     * This method remains abstract and must be implemented by subclasses.
     */
    @Override
    public abstract Maze generate(int rows, int columns);

    /**
     * Measures the time (in milliseconds) it takes to generate a maze using the
     * generate method.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return The time in milliseconds it took to generate the maze.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        this.generate(rows, columns);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
