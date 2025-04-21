package algorithms.search;
import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    @Override
    public String getName() {
        return "Breadth First Search";
    }

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

    @Override
    public int getNumberOfNodesEvaluated() {
        return nodesEvaluated;
    }

    //flip the path
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