package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        int[][] map = maze.getMaze();
        boolean[][] visited = new boolean[rows][columns];
        Random rand = new Random();

        // 1. Fill all cells as walls
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                map[r][c] = 1;
            }
        }

        // 2. Choose random start position on edges
        Position start = getRandomEdgePosition(rows, columns, rand);
        visited[start.getRow()][start.getColumn()] = true;
        map[start.getRow()][start.getColumn()] = 0;
        maze.setStartPosition(start);

        // 3. Iterative DFS backtracking with neighbor-of-neighbor carving
        Stack<Position> stack = new Stack<>();
        stack.push(start);
        int[] dR = { -1, 1, 0, 0 };
        int[] dC = { 0, 0, -1, 1 };

        while (!stack.isEmpty()) {
            Position current = stack.peek();
            int cr = current.getRow();
            int cc = current.getColumn();

            List<Position> neighbors = new ArrayList<>();
            // consider cells two steps away
            for (int i = 0; i < 4; i++) {
                int nr = cr + dR[i] * 2;
                int nc = cc + dC[i] * 2;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < columns && !visited[nr][nc]) {
                    neighbors.add(new Position(nr, nc));
                }
            }

            if (!neighbors.isEmpty()) {
                Position chosen = neighbors.get(rand.nextInt(neighbors.size()));
                // carve through intermediate cell
                int mr = (cr + chosen.getRow()) / 2;
                int mc = (cc + chosen.getColumn()) / 2;
                map[mr][mc] = 0;
                visited[chosen.getRow()][chosen.getColumn()] = true;
                map[chosen.getRow()][chosen.getColumn()] = 0;
                stack.push(chosen);
            } else {
                stack.pop();
            }
        }

        // 4. Choose goal among reachable edge cells, distinct and not adjacent to start
        List<Position> edgeCells = new ArrayList<>();
        // top and bottom edges
        for (int c = 0; c < columns; c++) {
            if (map[0][c] == 0) edgeCells.add(new Position(0, c));
            if (map[rows - 1][c] == 0) edgeCells.add(new Position(rows - 1, c));
        }
        // left and right edges (excluding corners to avoid duplicates)
        for (int r = 1; r < rows - 1; r++) {
            if (map[r][0] == 0) edgeCells.add(new Position(r, 0));
            if (map[r][columns - 1] == 0) edgeCells.add(new Position(r, columns - 1));
        }
        // remove start itself and its immediate neighbors
        edgeCells.removeIf(p -> p.equals(start) || areAdjacent(start, p));

        if (edgeCells.isEmpty()) {
            throw new IllegalStateException("No valid edge cells available for goal");
        }

        Position goal;
        do {
            goal = edgeCells.get(rand.nextInt(edgeCells.size()));
        } while (!pathExists(start, goal, map));
        maze.setGoalPosition(goal);

        return maze;
    }

    /**
     * Checks if a path exists between start and goal in the given map using DFS.
     */
    private boolean pathExists(Position start, Position goal, int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Stack<Position> stack = new Stack<>();
        stack.push(start);
        visited[start.getRow()][start.getColumn()] = true;

        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};
        while (!stack.isEmpty()) {
            Position current = stack.pop();
            if (current.equals(goal)) {
                return true;
            }
            for (int i = 0; i < 4; i++) {
                int nr = current.getRow() + dR[i];
                int nc = current.getColumn() + dC[i];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited[nr][nc] && map[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    stack.push(new Position(nr, nc));
                }
            }
        }
        return false;
    }

    /**
     * Returns a random cell position on the edges of the maze.
     */
    private Position getRandomEdgePosition(int rows, int columns, Random rand) {
        int edge = rand.nextInt(4);
        int row = 0, col = 0;
        switch (edge) {
            case 0: // Top edge
                row = 0;
                col = rand.nextInt(columns);
                break;
            case 1: // Bottom edge
                row = rows - 1;
                col = rand.nextInt(columns);
                break;
            case 2: // Left edge
                row = rand.nextInt(rows);
                col = 0;
                break;
            case 3: // Right edge
                row = rand.nextInt(rows);
                col = columns - 1;
                break;
        }
        return new Position(row, col);
    }

    /**
     * Checks if two positions are adjacent (Manhattan distance = 1).
     */
    private boolean areAdjacent(Position p1, Position p2) {
        int rowDiff = Math.abs(p1.getRow() - p2.getRow());
        int colDiff = Math.abs(p1.getColumn() - p2.getColumn());
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
}
