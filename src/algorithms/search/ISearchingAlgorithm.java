package algorithms.search;

/**
 * Interface for searching algorithms.
 */
public interface ISearchingAlgorithm {

    /**
     * Returns the name of the search algorithm.
     * @return the name of the algorithm.
     */
    public String getName();

    /**
     * Solves the given searchable problem.
     * This method takes in a problem that implements the `ISearchable` interface and returns a solution to that problem.
     * @param searchable the problem to solve (contains start state, goal state, and possible moves).
     * @return the solution found (could be an empty solution if no path exists).
     */
    abstract Solution solve(ISearchable searchable);

    /**
     * Returns the number of nodes evaluated during the search process.
     * @return the number of nodes that were evaluated.
     */
    public int getNumberOfNodesEvaluated();
}
