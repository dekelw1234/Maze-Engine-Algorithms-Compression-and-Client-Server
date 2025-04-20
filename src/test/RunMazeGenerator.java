package test;

import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args) {
        // Create three mazes using different algorithms
        testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // print the time it takes for the algorithm to generate the maze
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(1000, 1000)));

        // generate a new maze
        Maze maze = mazeGenerator.generate(100, 100);

        // print the maze
        maze.print();

        // get the start position of the maze
        Position startPosition = maze.getStartPosition();

        // print the start position
        System.out.println(String.format("Start Position: %s", startPosition));

        // print the goal position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}
