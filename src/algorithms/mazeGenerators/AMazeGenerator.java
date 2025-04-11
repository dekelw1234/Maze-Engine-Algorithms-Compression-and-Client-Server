package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public abstract Maze generate(int rows, int columns);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
