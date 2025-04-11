package algorithms.mazeGenerators;

public  class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);

        // אתחול המבוך כך שכל התאים הם "דרך" (למשל, ערך 0)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                maze.getMaze()[row][col] = 0;
            }
        }

        // קביעת נקודת התחלה וסיום
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(rows - 1, columns - 1));

        return maze;
    }
}
