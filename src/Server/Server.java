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
    private Thread acceptThread;
    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop=false;
        this.threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getThreadPoolSize());
    }

    public void start(){

        if (acceptThread != null && acceptThread.isAlive()) return;

        try {
            this.serverSocket = new ServerSocket(port); //OPEN THE SERVER
            this.serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Server started on port " + port);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to open port " + port, e);
        }
        acceptThread = new Thread(this::for_start, "Server-Accept-" + port); //if other request will come
        acceptThread.start();


    }

    public void for_start(){
             while (!stop) {
                try {
                    // waiting for a new request
                    Socket clientSocket = this.serverSocket.accept();

                    // when request arrives, it transfers its handling to "someone else"
                    // and continues to wait for another order
                    threadPool.execute(() -> handleClient(clientSocket));

                } catch (SocketTimeoutException e) {
                    // continue
                } catch (IOException e) {
                    if (stop) break; // someone wants to stop it
                    throw new RuntimeException("Error while accepting client", e);
                }
            }
            threadPool.shutdown(); // don't get new things to do
            try {
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                    // waiting for all threads to finish what they are doing
                    threadPool.shutdownNow(); // force it
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
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
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to close server socket: " + e.getMessage());
        }
    }
}
