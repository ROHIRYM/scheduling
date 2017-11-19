import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Reader {
    private final int N;
    private static int counter;

    private JFrame jFrame;
    private eHandler handler;
    private ArrayList<JTextField> textFields;
    private ArrayList<ArrayList<JRadioButton>> arrayOfRadioGroups;
    private int radioGroupIndex;
    private JButton b;
    private ArrayList<ArrayList<String>> listOfSubjectTeachers;
    private ArrayList<String> finalSubjects;
    private ArrayList<Integer> finalHours;
    private ArrayList<String> finalTeachers;
    private ArrayList<String> allTeachers;

    public Reader(String s,
                  HashMap<String, ArrayList<String>> teachersOfCertainSubjects, int N, ArrayList<String> allTeachers) {
        this.allTeachers = allTeachers;
        this.N = N;
        jFrame = new JFrame(s);
        jFrame.setLayout(new GridLayout(0, this.N));
        textFields = new ArrayList<JTextField>();
        arrayOfRadioGroups = new ArrayList<ArrayList<JRadioButton>>();
        radioGroupIndex = 0;
        jFrame.add(new JLabel("Предмет:"));
        jFrame.add(new JLabel("К-ть годин/тиждень:"));
        jFrame.add(new JLabel("Викладач:"));
        fillLabels(this.N - 3);
        finalSubjects = new ArrayList<String>();
        listOfSubjectTeachers = new ArrayList<ArrayList<String>>();
        Iterator<Map.Entry<String, ArrayList<String>>> iterator = teachersOfCertainSubjects
                .entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<String>> pair = iterator.next();
            String subject = pair.getKey();
            finalSubjects.add(subject);
            jFrame.add(new JLabel(subject));
            JTextField tempField = new JTextField(2);
            textFields.add(tempField);
            jFrame.add(tempField);
            ButtonGroup group = new ButtonGroup();
            arrayOfRadioGroups.add(new ArrayList<JRadioButton>());
            ArrayList<String> teachers = pair.getValue();
            listOfSubjectTeachers.add(teachers);
            for (String teacher : teachers) {
                JRadioButton tempRadio = new JRadioButton(teacher);
                tempRadio.setSelected(true);
                arrayOfRadioGroups.get(radioGroupIndex).add(tempRadio);
                group.add(tempRadio);
                jFrame.add(tempRadio);
            }
            fillLabels(this.N - 2 - teachers.size());
            radioGroupIndex++;
        }
        fillLabels(this.N / 2);
        b = new JButton("Зберегти дані");
        jFrame.add(b);
        handler = new eHandler();
        b.addActionListener(handler);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(140 * this.N, 600);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }

    private void fillLabels(int n) {
        for (int i = 0; i < n; i++) {
            jFrame.add(new JLabel());
        }
    }

    public static void setCounter(int counter) {
        Reader.counter = counter;
    }

    public class eHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == b) {
                    finalHours = determineFinalHours();
                    finalTeachers = determineFinalTeachers();
//					printInfo();
                    jFrame.setVisible(false);
                    counter--;
                    if (counter == 0) {
                        new TimeTable(allTeachers);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Введіть в поле число!");
            }
        }
    }

    private ArrayList<Integer> determineFinalHours() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (JTextField textField : textFields) {
            result.add(Integer.parseInt(textField.getText()));
        }
        return result;
    }

    private ArrayList<String> determineFinalTeachers() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < listOfSubjectTeachers.size(); i++) {
            ArrayList<String> certainTeachers = listOfSubjectTeachers.get(i);
            ArrayList<JRadioButton> certainRadios = arrayOfRadioGroups.get(i);
            for (int j = 0; j < certainTeachers.size(); j++) {
                if (certainRadios.get(j).isSelected()) {
                    result.add(certainTeachers.get(j));
                }
            }
        }
        return result;
    }

    public ArrayList<String> getFinalSubjects() {
        return finalSubjects;
    }

    public ArrayList<Integer> getFinalHours() {
        return finalHours;
    }

    public ArrayList<String> getFinalTeachers() {
        return finalTeachers;
    }
}
