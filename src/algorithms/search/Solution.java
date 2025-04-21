package algorithms.search;
import java.util.*;

/**
 * This class represents a solution to a search problem.
 */
public class Solution {

    ArrayList<AState> solutionPath; //the steps for the solution

    /**
     * Constructor for the Solution class.
     * Initializes the solution with a path of states.
     *
     * @param solutionPath the path of states from the start to the goal.
     */
    public Solution(ArrayList<AState> solutionPath){
        this.solutionPath=solutionPath;
    }
    /**
     * Returns the path of states that represents the solution.
     *
     * @return an ArrayList of states that form the solution path.
     */
    public ArrayList<AState> getSolutionPath(){
        return this.solutionPath;
    }
}
