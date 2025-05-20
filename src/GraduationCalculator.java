import java.util.List;

public class GraduationCalculator {
    public static int sum(List<Subject> list, Subject.SubjectType type) {
        return list.stream().filter(s -> s.getType() == type).mapToInt(Subject::getTotalCredits).sum();
    }

    public static int total(List<Subject> list) {
        return list.stream().mapToInt(Subject::getTotalCredits).sum();
    }

    public static String diagnose(User user) {
        var req = user.getRequirement();
        int total = total(user.getSubjects());
        int major = sum(user.getSubjects(), Subject.SubjectType.MAJOR);
        int elective = sum(user.getSubjects(), Subject.SubjectType.ELECTIVE);

        StringBuilder sb = new StringBuilder();
        sb.append("총 이수 학점: ").append(total).append(" / ").append(req.getTotal()).append("\n");
        sb.append("전공 학점: ").append(major).append(" / ").append(req.getMajor()).append("\n");
        sb.append("교양 학점: ").append(elective).append(" / ").append(req.getElective()).append("\n\n");

        if (total >= req.getTotal()) sb.append("✔ 총 졸업 학점 충족\n");
        else sb.append("✘ 총 졸업 학점 부족: ").append(req.getTotal() - total).append("학점\n");

        if (major >= req.getMajor()) sb.append("✔ 전공 학점 충족\n");
        else sb.append("✘ 전공 학점 부족: ").append(req.getMajor() - major).append("학점\n");

        if (elective <= req.getElective()) sb.append("✔ 교양 학점 범위 내\n");
        else sb.append("✘ 교양 학점 초과: ").append(elective - req.getElective()).append("학점\n");

        return sb.toString();
    }
}
