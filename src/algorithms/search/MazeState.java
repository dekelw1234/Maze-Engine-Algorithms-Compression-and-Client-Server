package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

/**
 * Represents a state in the maze during a search algorithm.
 */
public class MazeState extends AState {

    private Position position;

    /**
     * Constructs a new `MazeState` object with the specified position, parent step (fatherStep), and cost.
     * @param position the position of this state in the maze.
     * @param fatherStep the previous state (father step) from which we arrived at this state.
     * @param cost the cost to reach this state.
     */
    public MazeState(Position position, MazeState fatherStep,int cost){

        this.position=position;
        this.stateView="( " +position.getRowIndex() + " , "+position.getColumnIndex()+" )";
        this.fatherStep=fatherStep;
        this.cost=cost;

    }
    /**
     * Returns the position of this state in the maze.
     * @return the position of the state.
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Compares this `MazeState` to another object for equality.
     * Two `MazeState` objects are considered equal if they represent the same position in the maze (i.e., same row and column indices).
     * @param Obj the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object Obj) {
        if (this == Obj) return true;
        if (Obj == null) return false;
        MazeState other= (MazeState) Obj;
        return this.position.getRowIndex() == other.position.getRowIndex() && this.position.getColumnIndex() == other.position.getColumnIndex();
    }
    /**
     * Returns a hash code value for this `MazeState`.
     * The hash code is computed based on the position of the state, using the row and column indices of the `Position` object.
     * @return the hash code for this state.
     */
    @Override
    public int hashCode() {
        return Objects.hash(position.getRowIndex(), position.getColumnIndex());
    }
}

