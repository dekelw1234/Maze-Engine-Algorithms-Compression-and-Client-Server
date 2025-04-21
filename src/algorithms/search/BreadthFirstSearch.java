package algorithms.search;
import java.util.*;

/**
 * Represents the Breadth First Search algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    /**
     * Returns the name of the search algorithm.
     * @return the name of the algorithm ("Breadth First Search").
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }

    /**
     * Solves the given searchable problem using the Breadth First Search algorithm.
     * @param searchable the searchable problem to solve (contains start and goal states).
     * @return the solution found (or an empty solution if no path exists).
     */
    @Override
    public Solution solve(ISearchable searchable) {

        if (searchable == null)
            return null;

        //initialize the parameters for solving
        AState start = searchable.getStartState();
        AState goal = searchable.getGoalState();

        // Create a queue for BFS
        Queue<AState> queue = new LinkedList<>();

        // Initially mark all the vertices as not visited
        HashSet<AState> visited = new HashSet<>(); //Group (no duplicated member)

        // Mark source node as visited and enqueue it
        queue.add(start);
        visited.add(start);

        // Iterate over the queue
        while (!queue.isEmpty()) {

            // Dequeue a vertex from queue and store it
            AState current = queue.poll();
            // Increment the number of nodes evaluated
            this.nodesEvaluated++;

            if (current.equals(goal)) {
                return backTrace(current); //built the solution
            }

            // Get all adjacent vertices of the dequeued
            // vertex curr If an adjacent has not been
            // visited, mark it visited and enqueue it
            List<AState> adjacent = searchable.getAllPossibleStates(current);

            for (AState state : adjacent) {
                if (!visited.contains(state)) {
                    state.setFatherStep(current);
                    visited.add(state);
                    queue.add(state);
                }
            }
        }
        this.solution=new Solution(new ArrayList<>());
        return this.solution; // no path found
    }
    /**
     * Returns the number of nodes that have been evaluated during the search.
     * @return the number of nodes evaluated.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return nodesEvaluated;
    }

    /**
     * Reconstructs the solution path by tracing back from the goal state to the start state.
     * @param goal the goal state.
     * @return the solution containing the path from start to goal.
     */
    private Solution backTrace(AState goal) {
        ArrayList<AState> path = new ArrayList<>();
        AState current = goal;

        while (current != null) {
            path.add(current);
            current = current.getFatherStep();
        }

        Collections.reverse(path);
        this.solution=new Solution(path);
        return this.solution;
    }
}