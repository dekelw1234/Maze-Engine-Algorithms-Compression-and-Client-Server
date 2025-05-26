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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient); //It important to creat it first so the client will get the header so he can move on

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

            //3. compresses it using MyCompressorOutputStream > into temp stream
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrayOut);
            compressor.write(maze.toByteArray()); //convert the maze to byte array
            compressor.flush();
            compressor.close();

            byte[] compressedMaze = byteArrayOut.toByteArray();  //compress it

            //4. sends back a byte[] array representing the generated maze  > put into the main stream
            objectOutputStream.writeObject(compressedMaze); //send it
            objectOutputStream.flush();//make buffer empty
            objectOutputStream.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


