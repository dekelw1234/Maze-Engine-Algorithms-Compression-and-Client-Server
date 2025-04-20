package algorithms.search;

public interface ISearchingAlgorithm {

    public String getName();
    abstract Solution solve(ISearchable searchable);
    public int getNumberOfNodesEvaluated();
}
