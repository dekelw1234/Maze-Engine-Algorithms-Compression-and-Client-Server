package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private Position position;

    public MazeState(Position position, MazeState fatherStep,int cost){

        this.position=position;
        this.stateView="( " +position.getRow() + " , "+position.getColumn()+" )";
        this.fatherStep=fatherStep;
        this.cost=cost;

    }

    public Position getPosition(){
        return this.position;
    }
}

