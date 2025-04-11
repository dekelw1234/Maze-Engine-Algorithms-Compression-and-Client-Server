package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        // כאן נבנה מבוך פשוט
        Maze maze = new Maze(rows, columns);

        // 1) אתחול המערך ב-0 (כל התאים כ"דרך")
        //    אפשר גם כברירת מחדל זה נעשה ב-Java, אבל לפעמים מוסיפים ליתר ביטחון

        // 2) צרו "נתיב" פשוט לדוגמה:
        //    לדוגמה, ניצור שביל לאורך השורה הראשונה עד סוף המבוך,
        //    ואז נרד למטה בטור האחרון עד השורה האחרונה,
        //    וכך נקבל דרך "L" פשוטה...
        for (int col = 0; col < columns; col++) {
            maze.getMaze()[0][col] = 0;  // דרך
        }
        for (int row = 0; row < rows; row++) {
            maze.getMaze()[row][columns - 1] = 0;  // דרך
        }

        // 3) אפשר להוסיף קירות בשאר המקומות
        // לדוגמה – נמלא 1 בכל היתר (בחלק מהמקומות)
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns - 1; j++) {
                maze.getMaze()[i][j] = 1;  // קיר
            }
        }

        // 4) הגדרת נקודת התחלה וסיום
        //    לדוגמה: start = (0, 0), goal = (rows-1, columns-1)
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(rows - 1, columns - 1));

        return maze;
    }

}
