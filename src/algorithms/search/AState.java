package algorithms.search;

//מצב הוא פשוט מיקום במבוך, אבל המחלקה הזו אמורה להיות כללית, כדי לשמש גם לבעיות אחרות בעתיד
public abstract class AState {

    protected String stateView; //representing the state
    protected AState fatherStep; //where we came from
    protected int cost; //how many step till now

    public String getStateView() {
        return this.stateView;
    }
    public AState getFatherStep() {
        return this.fatherStep;
    }

    public void setCost(int cost){
        this.cost=cost;
    }

    public void setFatherStep(AState fatherStep) {
        this.fatherStep=fatherStep;
    }

    public int getCost() {
        return this.cost;
    }
}
