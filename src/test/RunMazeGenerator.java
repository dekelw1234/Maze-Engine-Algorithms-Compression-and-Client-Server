package test;

import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args) {
        // יצירת שלושה מבוכים עם אלגוריתמים שונים
        testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // מדפיס את הזמן שלוקח לאלגוריתם להריץ את יצירת המבוך
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(1000, 1000)));

        // יצירת מבוך חדש
        Maze maze = mazeGenerator.generate(15, 15);

        // הדפסת המבוך
        maze.print();

        // קבלת נקודת התחלה של המבוך
        Position startPosition = maze.getStartPosition();

        // הדפסת נקודת התחלה
        System.out.println(String.format("Start Position: %s", startPosition));

        // הדפסת נקודת סיום
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}
