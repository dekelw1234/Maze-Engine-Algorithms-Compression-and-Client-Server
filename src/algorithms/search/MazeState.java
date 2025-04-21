package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

public class MazeState extends AState {

    private Position position;

    public MazeState(Position position, MazeState fatherStep,int cost){

        this.position=position;
        this.stateView="( " +position.getRowIndex() + " , "+position.getColumnIndex()+" )";
        this.fatherStep=fatherStep;
        this.cost=cost;

    }

    public Position getPosition(){
        return this.position;
    }

    @Override
    public boolean equals(Object Obj) {
        if (this == Obj) return true;
        if (Obj == null) return false;
        MazeState other= (MazeState) Obj;
        return this.position.getRowIndex() == other.position.getRowIndex() && this.position.getColumnIndex() == other.position.getColumnIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(position.getRowIndex(), position.getColumnIndex());
    }
}

