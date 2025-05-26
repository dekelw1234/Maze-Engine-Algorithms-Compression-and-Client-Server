package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.security.MessageDigest;


public class ServerStrategySolveSearchProblem implements IServerStrategy{


    /* 1. receives from the client a compressed Maze object
       2. check if the solution already exist
       3. if not - solve it
       4. save it in a file
       5. return Solution object
     */

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            //1.  receives from the client a Maze object
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient); //read the serial object
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient);

            Object received = objectInputStream.readObject(); //convert it to object

            Maze maze=null;
            //check if it is a maze
            if (received instanceof Maze) {
                maze = (Maze) received; //convert it to maze
            } else{
                System.out.println("Invalid input");
                return;
            }

            //2. check if the solution already exist
            String hash = generateUniqueName(maze.toByteArray());
            File solutionFile = new File(System.getProperty("java.io.tmpdir"), hash + ".sol");

            if (solutionFile.exists())
            {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(solutionFile))) {
                    Solution sol = (Solution) in.readObject();
                    objectOutputStream.writeObject(sol);
                    objectOutputStream.flush();
                    return;
                }
            }

            //3. if not - solve it by the given algorithm
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

            //4. save it in a file
            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //create a temp dir once (not with every running)
            solutionFile = new File(tempDirectoryPath, hash + ".sol");
            try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(solutionFile))) {
                fileOut.writeObject(sol);
            }

            //5. return Solution object
            objectOutputStream.writeObject(sol);
            objectOutputStream.flush();
            objectOutputStream.close();

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //get representation of a maze and return unique name
    private String generateUniqueName(byte[] maze) { //unique name called "hash"
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //generate hash type SHA-256
            byte[] hashBytes = digest.digest(maze);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // return the name

        } catch (Exception e) {
            throw new RuntimeException("generateUniqueName failed", e);
        }
    }
}
