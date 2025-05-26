package algorithms.search;

import java.io.Serializable;

/**
 * Represents a state in a problem-solving process.
 * This class is designed to be general, so it can be used for different types of problems in the future.
 */

public abstract class AState implements Serializable {


    protected String stateView; //representing the state
    protected AState fatherStep; //where we came from
    protected int cost; //how many step till now

    /**
     * Returns a string representation of the state.
     * @return the state view (e.g., position or other representation).
     */
    public String getStateView() {
        return this.stateView;
    }

    /**
     * Returns the state we came from.
     * @return the father state (previous state).
     */
    public AState getFatherStep() {
        return this.fatherStep;
    }

    /**
     * Sets the cost (number of steps) to reach this state.
     * @param cost the cost to set.
     */
    public void setCost(int cost){
        this.cost=cost;
    }

    /**
     * Sets the father step (previous state) of this state.
     * @param fatherStep the father state to set.
     */
    public void setFatherStep(AState fatherStep) {
        this.fatherStep=fatherStep;
    }

    /**
     * Returns the cost (number of steps) taken to reach this state.
     * @return the cost.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns a string representation of the state (as defined by the stateView).
     * @return the string representation of the state.
     */
    @Override
    public String toString(){
        return this.getStateView();
    }

}
