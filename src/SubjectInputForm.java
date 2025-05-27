import javax.swing.*;
import java.awt.*;

public class SubjectInputForm extends JPanel {
    public JTextField credit1 = new JTextField("0", 3);
    public JTextField credit2 = new JTextField("0", 3);
    public JTextField credit3 = new JTextField("0", 3);

    public SubjectInputForm(String title) {
        setLayout(new GridLayout(4, 2));
        setBorder(BorderFactory.createTitledBorder(title));
        add(new JLabel("1학점 과목 수:")); add(credit1);
        add(new JLabel("2학점 과목 수:")); add(credit2);
        add(new JLabel("3학점 과목 수:")); add(credit3);
    }
}

