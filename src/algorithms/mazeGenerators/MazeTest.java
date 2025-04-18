package algorithms.mazeGenerators;

public class MazeTest {
    public static void main(String[] args) {
        // Define maze dimensions for testing
        int rows = 1000;
        int columns = 1000;

        // ----- Test EmptyMazeGenerator -----
        System.out.println("----- Testing EmptyMazeGenerator -----");
        IMazeGenerator emptyGen = new EmptyMazeGenerator();
        Maze emptyMaze = emptyGen.generate(rows, columns);
        System.out.println("Empty Maze:");
        // Print the maze using its toString() method
        emptyMaze.print();
        System.out.println("Generation time: "
                + emptyGen.measureAlgorithmTimeMillis(rows, columns) + " ms\n");

        // ----- Test SimpleMazeGenerator -----
        System.out.println("----- Testing SimpleMazeGenerator -----");
        IMazeGenerator simpleGen = new SimpleMazeGenerator();
        Maze simpleMaze = simpleGen.generate(rows, columns);
        System.out.println("Simple Maze:");
        // Print the maze using its toString() method
        simpleMaze.print();
        System.out.println("Generation time: "
                + simpleGen.measureAlgorithmTimeMillis(rows, columns) + " ms");
    }
}
