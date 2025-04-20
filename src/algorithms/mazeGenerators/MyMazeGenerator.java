package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Maze generator using a randomized version of iterative DFS (Randomized depth-first search).
 */
public class MyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a random maze using a DFS-based algorithm.
     * Ensures that the start and goal positions are on the edges and have a valid path.
     *
     * @param rows    Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A Maze object with start and goal positions set and a valid path between them.
     */
    @Override
    public Maze generate(int rows, int columns) {
        Random rand = new Random();

        while (true) {
            Maze maze = new Maze(rows, columns);
            int[][] map = maze.getMaze();
            boolean[][] visited = new boolean[rows][columns];

            // initialize maze with walls (1)
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    map[r][c] = 1;
                }
            }

            // choose a random start position on the edge
            Position start = getRandomEdgePosition(rows, columns, rand);
            map[start.getRowIndex()][start.getColumnIndex()] = 0;
            visited[start.getRowIndex()][start.getColumnIndex()] = true;

            // DFS stack to carve the maze paths
            Stack<Position> stack = new Stack<>();
            stack.push(start);

            int[] dR = {-1, 1, 0, 0};
            int[] dC = {0, 0, -1, 1};

            // DFS to carve paths
            while (!stack.isEmpty()) {
                Position current = stack.peek();
                int currRow = current.getRowIndex();
                int currCol = current.getColumnIndex();

                List<Position> potentialNeighbors = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    int nr = currRow + dR[i] * 2;
                    int nc = currCol + dC[i] * 2;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < columns && !visited[nr][nc]) {
                        potentialNeighbors.add(new Position(nr, nc));
                    }
                }

                if (!potentialNeighbors.isEmpty()) {
                    Position next = potentialNeighbors.get(rand.nextInt(potentialNeighbors.size()));
                    int interRow = (currRow + next.getRowIndex()) / 2;
                    int interCol = (currCol + next.getColumnIndex()) / 2;

                    map[interRow][interCol] = 0;
                    map[next.getRowIndex()][next.getColumnIndex()] = 0;
                    visited[next.getRowIndex()][next.getColumnIndex()] = true;
                    stack.push(next);
                } else {
                    stack.pop();
                }
            }

            // find valid goal positions (edges that are open and not too close to start)
            List<Position> openEdges = new ArrayList<>();
            for (int c = 0; c < columns; c++) {
                if (map[0][c] == 0) openEdges.add(new Position(0, c));
                if (map[rows - 1][c] == 0) openEdges.add(new Position(rows - 1, c));
            }
            for (int r = 1; r < rows - 1; r++) {
                if (map[r][0] == 0) openEdges.add(new Position(r, 0));
                if (map[r][columns - 1] == 0) openEdges.add(new Position(r, columns - 1));
            }

            openEdges.removeIf(p -> p.equals(start) || areAdjacent(start, p));

            for (Position candidate : openEdges) {
                if (pathExists(start, candidate, map)) {
                    maze.setStartPosition(start);
                    maze.setGoalPosition(candidate);
                    return maze;
                }
            }
            // if no valid goal found, regenerate
        }
    }

    /**
     * Checks if there is a path between start and goal positions using DFS.
     *
     * @param start Start position.
     * @param goal  Goal position.
     * @param map   The maze array.
     * @return True if a path exists, false otherwise.
     */
    private boolean pathExists(Position start, Position goal, int[][] map) {
        int n = map.length;
        int m = map[0].length;
        boolean[][] seen = new boolean[n][m];
        Stack<Position> stack = new Stack<>();
        stack.push(start);
        seen[start.getRowIndex()][start.getColumnIndex()] = true;

        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};

        while (!stack.isEmpty()) {
            Position curr = stack.pop();
            if (curr.equals(goal)) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr.getRowIndex() + dR[i];
                int nc = curr.getColumnIndex() + dC[i];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !seen[nr][nc] && map[nr][nc] == 0) {
                    seen[nr][nc] = true;
                    stack.push(new Position(nr, nc));
                }
            }
        }
        return false;
    }

    /**
     * Returns a random position from the edges (top, bottom, left, right).
     *
     * @param rows    Maze row count.
     * @param columns Maze column count.
     * @param rand    Random instance.
     * @return A position located on the edge of the maze.
     */
    private Position getRandomEdgePosition(int rows, int columns, Random rand) {
        int edge = rand.nextInt(4); // 0=Top, 1=Right, 2=Bottom, 3=Left
        int row = 0, column = 0;

        switch (edge) {
            case 0:
                row = 0;
                column = rand.nextInt(columns);
                break;
            case 1:
                row = rand.nextInt(rows);
                column = columns - 1;
                break;
            case 2:
                row = rows - 1;
                column = rand.nextInt(columns);
                break;
            case 3:
                row = rand.nextInt(rows);
                column = 0;
                break;
        }

        return new Position(row, column);
    }

    /**
     * Checks whether two positions are adjacent (one cell away).
     *
     * @param p1 First position.
     * @param p2 Second position.
     * @return True if the positions are adjacent (orthogonally), false otherwise.
     */
    private boolean areAdjacent(Position p1, Position p2) {
        int dr = Math.abs(p1.getRowIndex() - p2.getRowIndex());
        int dc = Math.abs(p1.getColumnIndex() - p2.getColumnIndex());
        return (dr == 1 && dc == 0) || (dr == 0 && dc == 1);
    }
}
