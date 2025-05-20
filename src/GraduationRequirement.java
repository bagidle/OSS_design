public class GraduationRequirement {
    private int total, major, elective;

    public GraduationRequirement(int total, int major, int elective) {
        this.total = total;
        this.major = major;
        this.elective = elective;
    }

    public int getTotal() { return total; }
    public int getMajor() { return major; }
    public int getElective() { return elective; }
}