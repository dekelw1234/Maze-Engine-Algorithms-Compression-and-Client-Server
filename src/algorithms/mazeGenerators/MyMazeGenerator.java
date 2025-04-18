package algorithms.mazeGenerators;

import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        int[][] mazeArray = maze.getMaze();

        // אתחול המבוך: כל התאים הם קירות (1)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mazeArray[i][j] = 1;  // כל התאים הם קירות (1)
            }
        }

        // יצירת מערך של כל הקצוות
        Random rand = new Random();
        Position start = getRandomEdgePosition(rows, columns, rand);
        Position goal = getRandomEdgePosition(rows, columns, rand);

        // להימנע ממקרה שבו התחלה וסיום יהיו באותו מקום
        while (start.equals(goal)) {
            goal = getRandomEdgePosition(rows, columns, rand);
        }

        // לוודא שהנקודות לא יהיו ליד אחת את השנייה
        while (areAdjacent(start, goal)) {
            goal = getRandomEdgePosition(rows, columns, rand);
        }

        // הגדרת נקודות התחלה וסיום במבוך
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        // ביצוע חיפוש לעומק (DFS) בין נקודת התחלה לנקודת סיום
        Stack<Position> stack = new Stack<>();
        stack.push(start);

        // מערך לשמירת ביקורים בתאים
        boolean[][] visited = new boolean[rows][columns];
        visited[start.getRow()][start.getColumn()] = true;

        // רשימות אפשריות של תנועות (למעלה, למטה, שמאלה, ימינה)
        int[] rowDirection = {-1, 1, 0, 0};
        int[] colDirection = {0, 0, -1, 1};

        // יצירת הדרך בעזרת DFS, כל תא בדרך יהיה 0
        while (!stack.isEmpty()) {
            Position current = stack.peek();
            boolean hasUnvisitedNeighbor = false;

            // לבדוק את השכנים של המיקום הנוכחי
            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + rowDirection[i];
                int newCol = current.getColumn() + colDirection[i];

                // אם השכן חוקי וטרם ביקרנו בו
                if (isValid(newRow, newCol, rows, columns) && !visited[newRow][newCol]) {
                    mazeArray[newRow][newCol] = 0; // הפוך את התא לדרך
                    visited[newRow][newCol] = true;
                    stack.push(new Position(newRow, newCol));
                    hasUnvisitedNeighbor = true;
                    break; // אם מצאנו שכן לא מבוקר, נעבור אליו מיד
                }
            }

            // אם אין שכנים לא מבוקרים, חזור אחורה
            if (!hasUnvisitedNeighbor) {
                stack.pop();
            }

            // אם הגענו לנקודת הסיום, נעצור
            if (current.equals(goal)) {
                break;
            }
        }

        // מיקום התחלה וסיום יהיו בדרכים (0)
        mazeArray[start.getRow()][start.getColumn()] = 0;
        mazeArray[goal.getRow()][goal.getColumn()] = 0;

        return maze;
    }

    // בדיקת אם המיקום חוקי בתוך גבולות המבוך
    private boolean isValid(int row, int col, int rows, int columns) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

    // בדוק אם שתי נקודות סמוכות (באותו רווח: אנכי או אופקי)
    private boolean areAdjacent(Position start, Position goal) {
        int rowDiff = Math.abs(start.getRow() - goal.getRow());
        int colDiff = Math.abs(start.getColumn() - goal.getColumn());
        // אם ההפרש הוא 1 בשורה או בעמודה, אז הן סמוכות
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    // מתודה לבחור מיקום אקראי מתוך הקצוות
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
