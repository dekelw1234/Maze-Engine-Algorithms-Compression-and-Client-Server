package algorithms.mazeGenerators;

public  class EmptyMazeGenerator extends AMazeGenerator{

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
