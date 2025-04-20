package algorithms.search;
import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private int nodesEvaluated = 0;

    @Override
    public String getName() {
        return "Depth First Search";
    }

    @Override
    public Solution solve(ISearchable searchable) {

        if (searchable == null)
            return null;

        // Initialize the parameters for solving
        AState start = searchable.getStartState();
        AState goal = searchable.getGoalState();

        // Create a stack for DFS
        Stack<AState> stack = new Stack<>();

        // Initially mark all the vertices as not visited
        HashSet<AState> visited = new HashSet<>(); // Group (no duplicated member)

        // Mark source node as visited and push it
        stack.push(start);
        visited.add(start);

        // Iterate over the stack
        while (!stack.isEmpty()) {

            // Pop a vertex from stack and store it
            AState current = stack.pop();

            // Increment the number of nodes evaluated
            this.nodesEvaluated++;

            if (current.equals(goal)) {
                return backTrace(current); // Build the solution
            }

            // Get all adjacent vertices of the popped vertex
            List<AState> adjacent = searchable.getAllPossibleStates(current);

            for (AState state : adjacent) {
                if (!visited.contains(state)) {
                    state.setFatherStep(current);
                    visited.add(state);
                    stack.push(state);
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
