package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;


public class ServerStrategySolveSearchProblem implements IServerStrategy{


    /* 1. receives from the client a compressed Maze object
       2. solve it
       4. return Solution object
     */

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            //1.  receives from the client a Maze object
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient); //read the serial object
            Object received = objectInputStream.readObject(); //convert it to object

            Maze maze=null;
            //check if it is a maze
            if (received instanceof Maze) {
                maze = (Maze) received; //convert it to maze
            } else{
                System.out.println("Invalid input");
                return;
            }

            //2. solve it by the given algorithm
            SearchableMaze searchableMaze = new SearchableMaze(maze);

            String algorithmName=Configurations.getInstance().getMazeSearchingAlgorithm();
            ISearchingAlgorithm algo;

            switch (algorithmName) {
                case "BestFirstSearch":
                    algo = new BestFirstSearch();
                    break;
                case "BreadthFirstSearch":
                    algo = new BreadthFirstSearch();
                    break;
                case "DepthFirstSearch":
                    algo = new DepthFirstSearch();
                    break;
                default:
                    System.out.println("Invalid required algorithm");
                    return;
            }

            Solution sol = algo.solve(searchableMaze);

            // 3. return Solution object
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient);
            objectOutputStream.writeObject(sol);
            objectOutputStream.flush();
            objectOutputStream.close();

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
