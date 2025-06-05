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

        // 1단계: 졸업 요건 입력 화면
        JPanel page1 = new JPanel(new GridLayout(7, 1));
        JTextField totalField = new JTextField();
        JTextField majorField = new JTextField();
        JTextField electiveField = new JTextField();
        JButton toElectivePage = new JButton("다음 (교양 입력)");

        page1.add(new JLabel("총 졸업 학점 입력")); page1.add(totalField);
        page1.add(new JLabel("전공 최소 학점 입력")); page1.add(majorField);
        page1.add(new JLabel("교양 최대 학점 입력")); page1.add(electiveField);
        page1.add(toElectivePage);

        // 2단계: 교양 입력 화면
        JPanel page2 = new JPanel();
        page2.setLayout(new BoxLayout(page2, BoxLayout.Y_AXIS));
        SubjectInputForm electiveForm = new SubjectInputForm("교양 과목 입력");
        JButton toMajorPage = new JButton("다음 (전공 입력)");

        page2.add(electiveForm);
        page2.add(toMajorPage);

        // 3단계: 전공 입력 및 결과 화면
        JPanel page3 = new JPanel();
        page3.setLayout(new BoxLayout(page3, BoxLayout.Y_AXIS));
        SubjectInputForm majorForm = new SubjectInputForm("전공 과목 입력");
        JTextArea resultArea = new JTextArea(6, 30);
        resultArea.setEditable(false);
        JButton calcBtn = new JButton("계산하기");

        page3.add(majorForm);
        page3.add(calcBtn);
        page3.add(new JScrollPane(resultArea));

        // 페이지 전환 및 동작 연결 (입력 검증 강화)
        toElectivePage.addActionListener(e -> {
            try {
                // 입력값 검증
                if (!InputValidator.isPositiveInteger(totalField.getText())) {
                    JOptionPane.showMessageDialog(frame, "총 졸업 학점은 양의 정수를 입력해주세요!");
                    return;
                }
                if (!InputValidator.isNonNegativeInteger(majorField.getText())) {
                    JOptionPane.showMessageDialog(frame, "전공 학점은 0 이상의 정수를 입력해주세요!");
                    return;
                }
                if (!InputValidator.isNonNegativeInteger(electiveField.getText())) {
                    JOptionPane.showMessageDialog(frame, "교양 학점은 0 이상의 정수를 입력해주세요!");
                    return;
                }

                int t = Integer.parseInt(totalField.getText().trim());
                int m = Integer.parseInt(majorField.getText().trim());
                int e1 = Integer.parseInt(electiveField.getText().trim());

                // 논리적 검증
                if (m > t) {
                    JOptionPane.showMessageDialog(frame, "전공 학점이 총 졸업 학점보다 클 수 없습니다!");
                    return;
                }
                if (e1 > t) {
                    JOptionPane.showMessageDialog(frame, "교양 학점이 총 졸업 학점보다 클 수 없습니다!");
                    return;
                }

                user.setRequirement(new GraduationRequirement(t, m, e1));
                user.clearSubjects();
                majorInputAdded = false;
                layout.next(container);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "입력 오류가 발생했습니다: " + ex.getMessage());
            }
        });

        toMajorPage.addActionListener(e -> {
            try {
                // 입력값 검증
                if (!InputValidator.isNonNegativeInteger(electiveForm.credit1.getText())) {
                    JOptionPane.showMessageDialog(frame, "1학점 과목 수는 0 이상의 정수를 입력해주세요!");
                    return;
                }
                if (!InputValidator.isNonNegativeInteger(electiveForm.credit2.getText())) {
                    JOptionPane.showMessageDialog(frame, "2학점 과목 수는 0 이상의 정수를 입력해주세요!");
                    return;
                }
                if (!InputValidator.isNonNegativeInteger(electiveForm.credit3.getText())) {
                    JOptionPane.showMessageDialog(frame, "3학점 과목 수는 0 이상의 정수를 입력해주세요!");
                    return;
                }

                int e1 = Integer.parseInt(electiveForm.credit1.getText().trim());
                int e2 = Integer.parseInt(electiveForm.credit2.getText().trim());
                int e3 = Integer.parseInt(electiveForm.credit3.getText().trim());

                if (InputValidator.validCount(e1)) user.addSubject(new Subject(1, Subject.SubjectType.ELECTIVE, e1));
                if (InputValidator.validCount(e2)) user.addSubject(new Subject(2, Subject.SubjectType.ELECTIVE, e2));
                if (InputValidator.validCount(e3)) user.addSubject(new Subject(3, Subject.SubjectType.ELECTIVE, e3));

                layout.next(container);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "입력 오류가 발생했습니다: " + ex.getMessage());
            }
        });

        calcBtn.addActionListener(e -> {
            try {
                if (!majorInputAdded) {
                    // 입력값 검증
                    if (!InputValidator.isNonNegativeInteger(majorForm.credit1.getText())) {
                        JOptionPane.showMessageDialog(frame, "1학점 과목 수는 0 이상의 정수를 입력해주세요!");
                        return;
                    }
                    if (!InputValidator.isNonNegativeInteger(majorForm.credit2.getText())) {
                        JOptionPane.showMessageDialog(frame, "2학점 과목 수는 0 이상의 정수를 입력해주세요!");
                        return;
                    }
                    if (!InputValidator.isNonNegativeInteger(majorForm.credit3.getText())) {
                        JOptionPane.showMessageDialog(frame, "3학점 과목 수는 0 이상의 정수를 입력해주세요!");
                        return;
                    }

                    int m1 = Integer.parseInt(majorForm.credit1.getText().trim());
                    int m2 = Integer.parseInt(majorForm.credit2.getText().trim());
                    int m3 = Integer.parseInt(majorForm.credit3.getText().trim());

                    if (InputValidator.validCount(m1)) user.addSubject(new Subject(1, Subject.SubjectType.MAJOR, m1));
                    if (InputValidator.validCount(m2)) user.addSubject(new Subject(2, Subject.SubjectType.MAJOR, m2));
                    if (InputValidator.validCount(m3)) user.addSubject(new Subject(3, Subject.SubjectType.MAJOR, m3));
                    majorInputAdded = true;
                }
                resultArea.setText(GraduationCalculator.diagnose(user));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "계산 오류가 발생했습니다: " + ex.getMessage());
            }
        });

        container.add(page1);
        container.add(page2);
        container.add(page3);
        frame.add(container);
        frame.setVisible(true);
    }
}
