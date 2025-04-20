package algorithms.search;
import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm {

    private int nodesEvaluated = 0;

    @Override
    public String getName() {
        return "Best First Search";
    }

    @Override
    public Solution solve(ISearchable searchable) {

        if (searchable == null)
            return null;

        // Initialize the parameters for solving
        AState start = searchable.getStartState();
        AState goal = searchable.getGoalState();

        // Open list (priority queue)
        PriorityQueue<AState> open = new PriorityQueue<>(Comparator.comparingInt(AState::getCost));  // Min-heap by cost (or heuristic)

        // Close list
        HashSet<AState> close = new HashSet<>();

        // Add the start state to the open list
        open.add(start);

        // Iterate until open list is empty
        while (!open.isEmpty()) {
            // Get the node with the highest priority (lowest cost)
            AState current = open.poll();

            // Increment the number of nodes evaluated
            this.nodesEvaluated++;

            // Add the current node to the close list
            close.add(current);

            // If we reached the goal, return the solution
            if (current.equals(goal)) {
                return backTrace(current); // Build the solution
            }

            // Get all adjacent nodes (states)
            List<AState> adjacent = searchable.getAllPossibleStates(current);

            for (AState state : adjacent) {
                if (close.contains(state)) {
                    continue;  // Skip if already in close list
                }

                if (!open.contains(state)) {
                    // If the state is not in open list, calculate cost and parent
                    state.setFatherStep(current);
                    state.setCost(calculateHeuristic(state, goal));  // Or some other cost function
                    open.add(state);
                } else {
                    // If state is in open list, check if this path is better
                    // Compare the current path cost with the new one and update if shorter
                    if (state.getCost() > calculateHeuristic(state, goal)) {
                        state.setFatherStep(current);
                        state.setCost(calculateHeuristic(state, goal));
                    }
                }
            }
        }

        this.solution = new Solution(new ArrayList<>());
        return this.solution; // No path found
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return nodesEvaluated;
    }

    // Calculate heuristic
    private int calculateHeuristic(AState state, AState goal) {

        return state.getCost();
    }

    // Flip the path
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
