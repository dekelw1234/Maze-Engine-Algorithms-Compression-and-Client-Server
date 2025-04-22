package algorithms.search;
import algorithms.mazeGenerators.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class adapts a maze into a suitable format for search algorithms.
 */

//adapter -לוקח מבוך והופך אותו מתאים לחיפוש
public class SearchableMaze implements ISearchable {

    private Maze maze;

    /**
     * Constructs a new `SearchableMaze` object.
     * @param maze the maze to be adapted for searching.
     */
    public SearchableMaze(Maze maze) {

        if (maze==null){
            throw new IllegalArgumentException("Maze is null");
        }
        this.maze = maze;
    }

    /**
     * Returns the start state of the maze.
     * The start state is represented as a `MazeState` object based on the start position of the maze.
     * @return the start state of the maze.
     */
    @Override
    public AState getStartState() {
        Position start = maze.getStartPosition();
        return new MazeState(start,null,0);//מקבל פוזיציה וממיר אותה אחר כך למצב
    }
    /**
     * Returns the goal state of the maze.
     * The goal state is represented as a `MazeState` object based on the goal position of the maze.
     * @return the goal state of the maze.
     */
    @Override
    public AState getGoalState() {
        Position goal = maze.getGoalPosition();
        return new MazeState(goal,null,0); //מקבל פוזיציה וממיר אותה אחר כך למצב
    }

    /**
     * Returns a list of all possible states that can be reached from the given state.
     * This method checks the neighboring positions (up, down, left, right) to determine which states are valid.
     * A valid state is one that is within the bounds of the maze and is not a wall.
     * @param curState the current state from which possible moves are calculated.
     * @return a list of valid neighboring states that can be reached from the current state.
     */
    @Override
    public List<AState> getAllPossibleStates(AState curState) {

        if (curState==null){
            return null;
        }

        List<AState> optionalSteps = new LinkedList<>();//will contain everyone who can be moved from the state to it
        Position pos = ((MazeState) curState).getPosition();

        int[][] map = maze.getMaze(); //copy the maze to a 2D array
        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();

        int[] directionRow = {-1, 1, 0, 0};
        int[] directionCol = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + directionRow[i];
            int newCol = col + directionCol[i];

            //Checks that we haven't gone outside the boundaries of the maze and that it's not a wall
            if (newRow >= 0 && newRow < map.length &&
                    newCol >= 0 && newCol < map[0].length &&
                    map[newRow][newCol] == 0) {

                Position newposition=new Position(newRow,newCol);
                optionalSteps.add(new MazeState(newposition, ((MazeState) curState),curState.getCost()+1));
            }
        }

        return optionalSteps;
    }


}
