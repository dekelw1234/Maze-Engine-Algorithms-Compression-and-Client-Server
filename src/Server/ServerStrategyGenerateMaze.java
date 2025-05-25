package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.*;


public class ServerStrategyGenerateMaze implements IServerStrategy {

    /* 1. receives from the client an int[] array of size 2- rows & columns
       2. generates a maze according to the parameters
       3. compresses it using MyCompressorOutputStream
       4. sends back a byte[] array representing the generated maze
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {

            //1. receives from the client an int[] array of size 2- rows & columns
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient); //read the serial object
            Object received = objectInputStream.readObject(); //convert it to object

            int rows=0;
            int cols=0;

            //check if it is int[] array of size 2
            if (received instanceof int[]) {
                int[] maze = (int[]) received; //convert it to array

                if (maze.length != 2) {
                    // invalid input
                    System.out.println("Invalid dimensions array length: expected 2, got " + maze.length);
                    return;
                }

                rows = maze[0];
                cols = maze[1];
            }
            else {System.out.println("Invalid input");
                return;}

            Maze maze=null;

            //2. generates a maze according to the parameters
            String GeneratingAlgorithmName=Configurations.getInstance().getMazeGeneratingAlgorithm();

            AMazeGenerator generator;

            switch (GeneratingAlgorithmName) {
                case "MyMazeGenerator":

                    generator = new MyMazeGenerator();

                    break;
                case "SimpleMazeGenerator":

                    generator = new SimpleMazeGenerator();

                    break;
                case "EmptyMazeGenerator":

                    generator = new EmptyMazeGenerator();
                    break;
                default:
                    System.out.println("Invalid required algorithm");
                    return;
            }

            maze=generator.generate(rows,cols);

            //3. compresses it using MyCompressorOutputStream
            //4. sends back a byte[] array representing the generated maze

            MyCompressorOutputStream compressor=new MyCompressorOutputStream(outToClient);//send the stream where the compressed maze will be saved
            compressor.write(maze.toByteArray()); //convert the maze to byte array and compress it end send it

            compressor.flush(); //make buffer empty
            compressor.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


