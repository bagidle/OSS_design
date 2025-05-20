import java.util.ArrayList;
import java.util.List;

public class User {
    private GraduationRequirement requirement;
    private List<Subject> completedSubjects = new ArrayList<>();

    public void setRequirement(GraduationRequirement r) {
        this.requirement = r;
    }

    public void addSubject(Subject s) {
        completedSubjects.add(s);
    }

    public void clearSubjects() {
        completedSubjects.clear();
    }

    public List<Subject> getSubjects() {
        return completedSubjects;
    }

    public GraduationRequirement getRequirement() {
        return requirement;
    }
}
