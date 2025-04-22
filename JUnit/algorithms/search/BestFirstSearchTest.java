package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    BestFirstSearch BFS = new BestFirstSearch();
    MyMazeGenerator m = new MyMazeGenerator();

    @Test
    void getName() {
        assertEquals("Best First Search", BFS.getName());
    }

    @Test
    void solve() {
        Maze maze = m.generate(10, 13);

        ISearchable searchable = null;
        assertNull(BFS.solve(searchable), "doesn't handle with nulls");

        searchable = new SearchableMaze(maze);
        assertTrue(BFS.solve(searchable) instanceof Solution, "isn't instanceof Solution");
    }

    @Test
    void CheckSolution() {

        Maze maze = m.generate(10, 10);
        ISearchable searchable = new SearchableMaze(maze);

        Solution solution = BFS.solve(searchable);
        assertNotNull(solution, "Solution should not be null");
        assertFalse(solution.getSolutionPath().isEmpty(), "Solution path should not be empty");
    }

    @Test
    void getNumberOfNodesEvaluated() {

        Maze maze = m.generate(10, 12);
        ISearchable searchable = new SearchableMaze(maze);
        assertTrue(0 <= BFS.getNumberOfNodesEvaluated(), "Number of evaluated nodes should be positive"); //should be always positive number
    }

    //Expected same solution for same maze
    @Test
    void solveShouldBeDeterministic() {

        Maze maze = m.generate(10, 12);
        ISearchable searchable = new SearchableMaze(maze);

        Solution firstRun = BFS.solve(searchable);
        Solution secondRun = BFS.solve(searchable);

        assertEquals(firstRun.getSolutionPath(), secondRun.getSolutionPath(), "Expected same solution for same maze");
    }

    @Test
    void testTinyMaze2x2() {
        Maze maze = m.generate(3, 3);
        ISearchable searchable = new SearchableMaze(maze);

        Solution solution = BFS.solve(searchable);

        assertNotNull(solution);
    }

}