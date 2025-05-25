package Server;

import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    private static Configurations instance;

    //for each instance:
    private final int threadPoolSize;
    private final String mazeGeneratingAlgorithm;
    private final String mazeSearchingAlgorithm;

    private Configurations() {

        Properties properties=new Properties(); //java build in class which creates key-value pairs

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties"))
        {
            properties.load(input);
            threadPoolSize = Integer.parseInt(properties.getProperty("threadPoolSize", "1"));
            mazeGeneratingAlgorithm = properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
            mazeSearchingAlgorithm = properties.getProperty("mazeSearchingAlgorithm", "BreadthFirstSearch");


        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public String getMazeGeneratingAlgorithm() {
        return mazeGeneratingAlgorithm;
    }

    public String getMazeSearchingAlgorithm() {
        return mazeSearchingAlgorithm;
    }
}
