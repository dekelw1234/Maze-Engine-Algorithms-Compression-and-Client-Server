package algorithms.search;
import algorithms.mazeGenerators.*;

import java.util.LinkedList;
import java.util.List;

//adapter -לוקח מבוך והופך אותו מתאים לחיפוש
public class SearchableMaze implements ISearchable {

    private Maze maze;

    //בנאי
    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position start = maze.getStartPosition();
        return new MazeState(start,null,0);//מקבל פוזיציה וממיר אותה אחר כך למצב
    }

    @Override
    public AState getGoalState() {
        Position goal = maze.getGoalPosition();
        return new MazeState(goal,null,0); //מקבל פוזיציה וממיר אותה אחר כך למצב
    }

    //מגדירה מהן כל האפשרויות לנוע ממצב מסוים.
    //מקבלת מצב ואומר מאיפה אפשר לזוז ממנו
    @Override
    public List<AState> getAllPossibleStates(AState curState) {

        List<AState> optionalSteps = new LinkedList<>(); //יכיל את כל מי שאפשר לזוז מהמצב אליו
        Position pos = ((MazeState) curState).getPosition();

        int[][] map = maze.getMaze(); //מעתיק את המבוך אל מערך
        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();

        // תנועה אפשרית: למעלה, למטה, שמאלה, ימינה
        int[] directionRow = {-1, 1, 0, 0};
        int[] directionCol = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + directionRow[i];
            int newCol = col + directionCol[i];

            //בודק שלא יצאנו מהגבולות של המבוך וזה לא קיר
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
