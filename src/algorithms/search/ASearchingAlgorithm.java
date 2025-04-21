package algorithms.search;

/**
 * Abstract class for any searching algorithm.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    //Holds the solution if one is found.
    protected Solution solution;


     //The number of nodes that have been evaluated during the search.
    protected int nodesEvaluated = 0;

}
