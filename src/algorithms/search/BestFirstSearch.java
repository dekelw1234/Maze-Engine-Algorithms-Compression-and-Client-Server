package algorithms.search;
import java.util.*;


/**
 * Represents the Best First Search algorithm.
 */
public class BestFirstSearch extends ASearchingAlgorithm {

    private int nodesEvaluated = 0;

    /**
     * Returns the name of the search algorithm.
     * @return the name of the algorithm ("Best First Search").
     */
    @Override
    public String getName() {
        return "Best First Search";
    }

    /**
     * Solves the given searchable problem using the Best First Search algorithm.
     * @param searchable the searchable problem to solve (contains start and goal states).
     * @return the solution found (or an empty solution if no path exists).
     */
    @Override
    public Solution solve(ISearchable searchable) {

        if (searchable == null)
            return null;

        // initialize the start and goal states
        AState start = searchable.getStartState();
        AState goal = searchable.getGoalState();

        // open list (priority queue ordered by f(n) = g(n) + h(n))
        PriorityQueue<AState> open = new PriorityQueue<>(Comparator.comparingInt(AState::getCost));

        // closed list (visited states)
        HashSet<AState> closed = new HashSet<>();

        // initialize the start state
        start.setCost(0);
        open.add(start);

        while (!open.isEmpty()) {
            // remove the state with the lowest cost (f(n))
            AState current = open.poll();
            this.nodesEvaluated++;

            // if the goal is reached, reconstruct the solution path
            if (current.equals(goal)) {
                return backTrace(current);
            }

            // mark current state as visited
            closed.add(current);

            // explore all possible next states
            List<AState> neighbors = searchable.getAllPossibleStates(current);

            for (AState neighbor : neighbors) {
                if (closed.contains(neighbor)) {
                    continue;  // skip already visited states
                }

                // calculate tentative g(n) = g(current) + cost of moving (assume cost = 1)
                int currentG = current.getCost() - current.getCost();
                int tentativeG = currentG + 1;

                // Calculate total cost
                int totalCost = tentativeG + neighbor.getCost();

                // if neighbor not in open list OR this path is better than previous
                if (!open.contains(neighbor) || totalCost < neighbor.getCost()) {
                    neighbor.setFatherStep(current);   // Update parent
                    neighbor.setCost(totalCost);       // Update cost (f = g + h)

                    if (!open.contains(neighbor)) {
                        open.add(neighbor);            // Add to open list if not already there
                    }
                }
            }
        }

        // No path was found
        this.solution = new Solution(new ArrayList<>());
        return this.solution;
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
        this.solution = new Solution(path);
        return this.solution;
    }
}
