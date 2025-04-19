package algorithms.mazeGenerators;

import java.util.Random;
import java.util.Stack;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        int[][] mazeArray = maze.getMaze();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mazeArray[i][j] = 0;
            }
        }


        Random rand = new Random();
        Position start = getRandomEdgePosition(rows, columns, rand);
        Position goal = getRandomEdgePosition(rows, columns, rand);


        while (areAdjacent(start, goal)) {
            goal = getRandomEdgePosition(rows, columns, rand);
        }


        maze.setStartPosition(start);
        maze.setGoalPosition(goal);


        boolean pathFound = false;
        while (!pathFound) {

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    mazeArray[i][j] = 0;
                }
            }


            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (rand.nextInt(100) < 15) {
                        mazeArray[i][j] = 1;
                    }
                }
            }

            pathFound = checkPathExists(start, goal, mazeArray, rows, columns);
        }


        return maze;
    }


    private boolean checkPathExists(Position start, Position goal, int[][] mazeArray, int rows, int columns) {
        boolean[][] visited = new boolean[rows][columns];
        Stack<Position> stack = new Stack<>();
        stack.push(start);
        visited[start.getRow()][start.getColumn()] = true;


        int[] rowDirection = {-1, 1, 0, 0};
        int[] colDirection = {0, 0, -1, 1};


        while (!stack.isEmpty()) {
            Position current = stack.pop();

            if (current.equals(goal)) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + rowDirection[i];
                int newCol = current.getColumn() + colDirection[i];


                if (isValid(newRow, newCol, rows, columns) && mazeArray[newRow][newCol] == 0 && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    stack.push(new Position(newRow, newCol));
                }
            }
        }
        return false;
    }


    private boolean isValid(int row, int col, int rows, int columns) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }


    private boolean areAdjacent(Position start, Position goal) {
        int rowDiff = Math.abs(start.getRow() - goal.getRow());
        int colDiff = Math.abs(start.getColumn() - goal.getColumn());
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }


    private Position getRandomEdgePosition(int rows, int columns, Random rand) {
        int edge = rand.nextInt(4); // 0=Top, 1=Right, 2=Bottom, 3=Left
        int row = 0, column = 0;

        switch (edge) {
            case 0: // Top row
                row = 0;
                column = rand.nextInt(columns);
                break;
            case 1: // Right column
                row = rand.nextInt(rows);
                column = columns - 1;
                break;
            case 2: // Bottom row
                row = rows - 1;
                column = rand.nextInt(columns);
                break;
            case 3: // Left column
                row = rand.nextInt(rows);
                column = 0;
                break;
        }

        return new Position(row, column);
    }
}
