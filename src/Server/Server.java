package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private boolean stop;
    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop=false;
        this.threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getThreadPoolSize());
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {

                    Socket clientSocket = serverSocket.accept(); //waiting for a new request
                    threadPool.execute(() -> handleClient(clientSocket)); //when request arrives, it transfers its handling to "someone else" and continues to wait for another order
                } catch (SocketTimeoutException e) {
                    //time out - just continue to next loop iteration
                }
            }
            threadPool.shutdown(); //don't get new things to do
            try {
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) { //waiting to all threads to finish what they are doing
                    threadPool.shutdownNow(); //force it
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Server failed on port " + port, e);
        }
    }

    private void handleClient(Socket clientSocket){
        try {

            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();

        } catch (IOException e){
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    public void stop() {
        stop = true;
    }
}
