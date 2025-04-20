package algorithms.search;
import java.util.List;

//ממיר את המבוך מהפורמט שהוא נמצא בו לפורמט מתאים לחיפוש פתרון
public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState state);
}