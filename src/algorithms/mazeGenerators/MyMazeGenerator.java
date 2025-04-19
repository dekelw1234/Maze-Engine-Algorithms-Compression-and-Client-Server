package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Random rand = new Random();

        while (true) {
            Maze maze = new Maze(rows, columns);
            int[][] map = maze.getMaze();
            boolean[][] visited = new boolean[rows][columns];

            // אתחול המבוך: כל תא מתחיל כקיר
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    map[r][c] = 1;
                }
            }

            // בחירת נקודת התחלה: תא אקראי על שוליים
            Position start = getRandomEdgePosition(rows, columns, rand);
            map[start.getRow()][start.getColumn()] = 0;
            visited[start.getRow()][start.getColumn()] = true;

            // שימוש במחסנית ל־DFS איטרטיבי לבניית תעלות במבוך
            Stack<Position> stack = new Stack<>();
            stack.push(start);

            // אופציות אפשריות לתנועה מכל נקודה
            int[] dR = {-1, 1, 0, 0};
            int[] dC = {0, 0, -1, 1};

            // שבירת הקיר כך שכל תא פתוח יהיה מחובר - יצירת תעלה
            while (!stack.isEmpty()) {
                Position current = stack.peek();
                int currRow = current.getRow();
                int currCol = current.getColumn();

                // איסוף תאים שנמצאים במרחק "קפיצה" של שני צעדים ולא בוקרו
                List<Position> potentialNeighbors = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    int nr = currRow + dR[i] * 2;
                    int nc = currCol + dC[i] * 2;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < columns
                            && !visited[nr][nc]) {
                        potentialNeighbors.add(new Position(nr, nc));
                    }
                }

                if (!potentialNeighbors.isEmpty()) {
                    // בחירה אקראית בין התאים הפוטנציאליים
                    Position next = potentialNeighbors.get(rand.nextInt(potentialNeighbors.size()));

                    // שבירת הקיר שבין התא הנוכחי ל־next כדי ליצור מעבר
                    int interRow = (currRow + next.getRow()) / 2;
                    int interCol = (currCol + next.getColumn()) / 2;
                    map[interRow][interCol] = 0;

                    // סימון התו הבא כדרך וביקורו
                    map[next.getRow()][next.getColumn()] = 0;
                    visited[next.getRow()][next.getColumn()] = true;

                    // המשך ה־DFS מהתו הבא
                    stack.push(next);
                } else {
                    // אין עוד תאים פנויים, חזרה אחורה במסלול
                    stack.pop();
                }
            }

            // בחירת נקודת סיום חוקית על השוליים
            List<Position> openEdges = new ArrayList<>();
            for (int c = 0; c < columns; c++) {
                if (map[0][c] == 0) openEdges.add(new Position(0, c));
                if (map[rows - 1][c] == 0) openEdges.add(new Position(rows - 1, c));
            }
            for (int r = 1; r < rows - 1; r++) {
                if (map[r][0] == 0) openEdges.add(new Position(r, 0));
                if (map[r][columns - 1] == 0) openEdges.add(new Position(r, columns - 1));
            }

            // הסרת נקודת ההתחלה וכל תאים סמוכים לה כדי למנוע בחירתם כיעד
            openEdges.removeIf(p -> p.equals(start) || areAdjacent(start, p));

            // בחירת נקודת סיום: מריץ בדיקת מסלול תקין בין ההתחלה לסיום לפני שמחזיר את המבוך
            for (Position candidate : openEdges) {
                if (pathExists(start, candidate, map)) {
                    maze.setStartPosition(start);
                    maze.setGoalPosition(candidate);
                    return maze; // מבוך תקין נוצר
                }
            }
            // אם לא נמצא מועמד מתאים, מבוצע סיבוב חדש של יצירת מבוך עד לקבלת מבוך תקין
        }
    }


    //מוודא שיש מסלול בין שתי נקודות במפה באמצעות DFS
         private boolean pathExists(Position start, Position goal, int[][] map) {
        int n = map.length;
        int m = map[0].length;
        boolean[][] seen = new boolean[n][m];
        Stack<Position> stack = new Stack<>();
        stack.push(start);
        seen[start.getRow()][start.getColumn()] = true;

        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};
        while (!stack.isEmpty()) {
            Position curr = stack.pop();
            if (curr.equals(goal)) {
                return true;
            }
            for (int i = 0; i < 4; i++) {
                int nr = curr.getRow() + dR[i];
                int nc = curr.getColumn() + dC[i];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && !seen[nr][nc] && map[nr][nc] == 0) {
                    seen[nr][nc] = true;
                    stack.push(new Position(nr, nc));
                }
            }
        }
        return false;
    }


     // מחזיר מיקום אקראי מתוך תאי השוליים: עליון, תחתון, שמאלי או ימני

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


     // בודק מרחק  של יחידה אחת כדי לקבוע שכנים

    private boolean areAdjacent(Position p1, Position p2) {
        int dr = Math.abs(p1.getRow() - p2.getRow());
        int dc = Math.abs(p1.getColumn() - p2.getColumn());
        return (dr == 1 && dc == 0) || (dr == 0 && dc == 1);
    }
}
