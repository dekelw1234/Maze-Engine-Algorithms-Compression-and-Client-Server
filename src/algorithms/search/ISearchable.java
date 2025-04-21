package algorithms.search;
import java.util.List;

/**
 * Adopter - Interface for searchable problems.
 * This interface defines the methods that any problem (such as a maze) should implement to be solvable
 *  * by search algorithms.
 */


public interface ISearchable {

    /**
     * Returns the start state of the search problem.
     * @return the start state.
     */
    AState getStartState();

    /**
     * Returns the goal state of the search problem.
     * @return the goal state.
     */
    AState getGoalState();

    /**
     * Returns a list of all possible states that can be reached from the given state-
     * this method provides the neighboring states from a given state in the search problem.
     * @param state the state to get all possible next states from.
     * @return a list of adjacent states.
     */
    List<AState> getAllPossibleStates(AState state);
}