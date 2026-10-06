# Maze Engine – Algorithms, Compression and Client-Server (Part B)

The engine behind a JavaFX maze game: maze generation, search algorithms, a custom compression format and a multi-threaded client-server layer.

This is Part B of a three-part university project (Advanced Topics in Programming). The JavaFX game that uses this engine is in [ATP-Project-PartC](https://github.com/dekelw1234/ATP-Project-PartC).

Built by Dekel Winkler and [partner's name].

## Maze generation

| Generator | Description |
|---|---|
| `EmptyMazeGenerator` | A maze with no walls |
| `SimpleMazeGenerator` | Random walls |
| `MyMazeGenerator` | A perfect maze carved with **randomized iterative DFS** (using an explicit stack). Start and goal are placed on the edges, and the generator checks that a path exists between them. |

## Search algorithms

The search algorithms are generic: they work on any problem that implements the `ISearchable` interface, not only on mazes. `SearchableMaze` adapts a maze to that interface.

- **Breadth-First Search** (`BreadthFirstSearch`)
- **Depth-First Search** (`DepthFirstSearch`)
- **Best-First Search** (`BestFirstSearch`), using a priority queue ordered by state cost

Each algorithm returns a `Solution` (the path from start to goal) and reports how many states it evaluated.

## Compression

A maze is serialized to a byte array: a 12-byte header (rows, columns, start and goal positions, 2 bytes each) followed by one byte per cell. Two compression streams are implemented:

- `SimpleCompressorOutputStream`: run-length encoding (RLE)
- `MyCompressorOutputStream`: **LZW** compression

Each has a matching decompressor input stream.

## Client-server

- `Server` accepts TCP connections and handles each client in a **thread pool** (size set in `config.properties`), so several clients are served at once. It can be stopped cleanly.
- `ServerStrategyGenerateMaze` receives the maze dimensions and returns a new maze, compressed.
- `ServerStrategySolveSearchProblem` receives a maze and returns its solution. Solutions are **cached on disk**: the maze bytes are hashed with SHA-256, and if the same maze is requested again, the saved solution is returned instead of searching again.
- `Client` connects to a server and runs a pluggable `IClientStrategy`.

The server settings are read once from `resources/config.properties` through a singleton `Configurations` class:
```
threadPoolSize=5
mazeGeneratingAlgorithm=MyMazeGenerator
mazeSearchingAlgorithm=BestFirstSearch
```

## Testing

- JUnit tests for Best-First Search (`JUnit/algorithms/search/BestFirstSearchTest.java`)
- Runnable checks in `src/test/` for maze generation, search, compression and decompression, and client-server communication

## Project structure

```
src/
  algorithms/mazeGenerators/   maze model and generators
  algorithms/search/           generic search framework and algorithms
  IO/                          RLE and LZW compression streams
  Server/                      multi-threaded server, strategies, configuration
  Client/                      client and client strategy interface
  test/                        runnable checks
JUnit/                         unit tests
resources/                     config.properties
```
