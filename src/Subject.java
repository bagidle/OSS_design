public class Subject {
    public enum SubjectType { MAJOR, ELECTIVE }
    private int credits;
    private SubjectType type;
    private int count;

    public Subject(int credits, SubjectType type, int count) {
        this.credits = credits;
        this.type = type;
        this.count = count;
    }

    public int getTotalCredits() {
        return credits * count;
    }

    public SubjectType getType() {
        return type;
    }
}
