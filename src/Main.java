import javax.swing.*;
import java.awt.*;

public class Main {
    static JFrame frame;
    static CardLayout layout;
    static JPanel container;
    static User user = new User();
    static boolean majorInputAdded = false;

    public static void main(String[] args) {
        frame = new JFrame("졸업 학점 계산기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        layout = new CardLayout();
        container = new JPanel(layout);


        JPanel page1 = new JPanel(new GridLayout(7, 1));
        JTextField totalField = new JTextField();
        JTextField majorField = new JTextField();
        JTextField electiveField = new JTextField();
        JButton toElectivePage = new JButton("다음 (교양 입력)");

        page1.add(new JLabel("총 졸업 학점 입력")); page1.add(totalField);
        page1.add(new JLabel("전공 최소 학점 입력")); page1.add(majorField);
        page1.add(new JLabel("교양 최대 학점 입력")); page1.add(electiveField);
        page1.add(toElectivePage);


        JPanel page2 = new JPanel();
        page2.setLayout(new BoxLayout(page2, BoxLayout.Y_AXIS));
        SubjectInputForm electiveForm = new SubjectInputForm("교양 과목 입력");
        JButton toMajorPage = new JButton("다음 (전공 입력)");

        page2.add(electiveForm);
        page2.add(toMajorPage);


        JPanel page3 = new JPanel();
        page3.setLayout(new BoxLayout(page3, BoxLayout.Y_AXIS));
        SubjectInputForm majorForm = new SubjectInputForm("전공 과목 입력");
        JTextArea resultArea = new JTextArea(6, 30);
        resultArea.setEditable(false);
        JButton calcBtn = new JButton("계산하기");

        page3.add(majorForm);
        page3.add(calcBtn);
        page3.add(new JScrollPane(resultArea));


        toElectivePage.addActionListener(e -> {
            int t = Integer.parseInt(totalField.getText());
            int m = Integer.parseInt(majorField.getText());
            int e1 = Integer.parseInt(electiveField.getText());
            user.setRequirement(new GraduationRequirement(t, m, e1));
            user.clearSubjects();
            majorInputAdded = false;
            layout.next(container);
        });

        toMajorPage.addActionListener(e -> {
            int e1 = Integer.parseInt(electiveForm.credit1.getText());
            int e2 = Integer.parseInt(electiveForm.credit2.getText());
            int e3 = Integer.parseInt(electiveForm.credit3.getText());
            if (InputValidator.validCount(e1)) user.addSubject(new Subject(1, Subject.SubjectType.ELECTIVE, e1));
            if (InputValidator.validCount(e2)) user.addSubject(new Subject(2, Subject.SubjectType.ELECTIVE, e2));
            if (InputValidator.validCount(e3)) user.addSubject(new Subject(3, Subject.SubjectType.ELECTIVE, e3));
            layout.next(container);
        });

        calcBtn.addActionListener(e -> {
            if (!majorInputAdded) {
                int m1 = Integer.parseInt(majorForm.credit1.getText());
                int m2 = Integer.parseInt(majorForm.credit2.getText());
                int m3 = Integer.parseInt(majorForm.credit3.getText());
                if (InputValidator.validCount(m1)) user.addSubject(new Subject(1, Subject.SubjectType.MAJOR, m1));
                if (InputValidator.validCount(m2)) user.addSubject(new Subject(2, Subject.SubjectType.MAJOR, m2));
                if (InputValidator.validCount(m3)) user.addSubject(new Subject(3, Subject.SubjectType.MAJOR, m3));
                majorInputAdded = true;
            }
            resultArea.setText(GraduationCalculator.diagnose(user));
        });

        container.add(page1);
        container.add(page2);
        container.add(page3);
        frame.add(container);
        frame.setVisible(true);
    }
}
