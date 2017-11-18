import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Reader {
    private final int N;
    private static int counter;
    private JFrame jFrame;
    private Reader.eHandler handler;
    private ArrayList<JTextField> textFields;
    private ArrayList<ArrayList<JRadioButton>> arrayOfRadioGroups;
    private int radioGroupIndex;
    private JButton b;
    private ArrayList<ArrayList<String>> listOfSubjectTeachers;
    private ArrayList<String> finalSubjects;
    private ArrayList<Integer> finalHours;
    private ArrayList<String> finalTeachers;

    public Reader(String s, HashMap<String, ArrayList<String>> teachersOfCertainSubjects, int N) {
        this.N = N;
        this.jFrame = new JFrame(s);
        this.jFrame.setLayout(new GridLayout(0, this.N));
        this.textFields = new ArrayList();
        this.arrayOfRadioGroups = new ArrayList();
        this.radioGroupIndex = 0;
        this.jFrame.add(new JLabel("Предмет:"));
        this.jFrame.add(new JLabel("К-ть годин/тиждень:"));
        this.jFrame.add(new JLabel("Викладач:"));
        this.fillLabels(this.N - 3);
        this.finalSubjects = new ArrayList();
        this.listOfSubjectTeachers = new ArrayList();

        for(Iterator iterator = teachersOfCertainSubjects.entrySet().iterator(); iterator.hasNext(); ++this.radioGroupIndex) {
            Entry<String, ArrayList<String>> pair = (Entry)iterator.next();
            String subject = (String)pair.getKey();
            this.finalSubjects.add(subject);
            this.jFrame.add(new JLabel(subject));
            JTextField tempField = new JTextField(2);
            this.textFields.add(tempField);
            this.jFrame.add(tempField);
            ButtonGroup group = new ButtonGroup();
            this.arrayOfRadioGroups.add(new ArrayList());
            ArrayList<String> teachers = (ArrayList)pair.getValue();
            this.listOfSubjectTeachers.add(teachers);
            Iterator var11 = teachers.iterator();

            while(var11.hasNext()) {
                String teacher = (String)var11.next();
                JRadioButton tempRadio = new JRadioButton(teacher);
                tempRadio.setSelected(true);
                ((ArrayList)this.arrayOfRadioGroups.get(this.radioGroupIndex)).add(tempRadio);
                group.add(tempRadio);
                this.jFrame.add(tempRadio);
            }

            this.fillLabels(this.N - 2 - teachers.size());
        }

        this.fillLabels(this.N / 2);
        this.b = new JButton("Зберегти дані");
        this.jFrame.add(this.b);
        this.handler = new Reader.eHandler();
        this.b.addActionListener(this.handler);
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(3);
        this.jFrame.setSize(140 * this.N, 600);
        this.jFrame.setResizable(false);
        this.jFrame.setLocationRelativeTo((Component)null);
    }

    private void fillLabels(int n) {
        for(int i = 0; i < n; ++i) {
            this.jFrame.add(new JLabel());
        }

    }

    public static void setCounter(int counter) {
        counter = counter;
    }

    private ArrayList<Integer> determineFinalHours() {
        ArrayList<Integer> result = new ArrayList();
        Iterator var3 = this.textFields.iterator();

        while(var3.hasNext()) {
            JTextField textField = (JTextField)var3.next();
            result.add(Integer.parseInt(textField.getText()));
        }

        return result;
    }

    private ArrayList<String> determineFinalTeachers() {
        ArrayList<String> result = new ArrayList();

        for(int i = 0; i < this.listOfSubjectTeachers.size(); ++i) {
            ArrayList<String> certainTeachers = (ArrayList)this.listOfSubjectTeachers.get(i);
            ArrayList<JRadioButton> certainRadios = (ArrayList)this.arrayOfRadioGroups.get(i);

            for(int j = 0; j < certainTeachers.size(); ++j) {
                if (((JRadioButton)certainRadios.get(j)).isSelected()) {
                    result.add((String)certainTeachers.get(j));
                }
            }
        }

        return result;
    }

    public ArrayList<String> getFinalSubjects() {
        return this.finalSubjects;
    }

    public ArrayList<Integer> getFinalHours() {
        return this.finalHours;
    }

    public ArrayList<String> getFinalTeachers() {
        return this.finalTeachers;
    }

    public class eHandler implements ActionListener {
        public eHandler() {
        }

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == Reader.this.b) {
                    Reader.this.finalHours = Reader.this.determineFinalHours();
                    Reader.this.finalTeachers = Reader.this.determineFinalTeachers();
                    Reader.this.jFrame.setVisible(false);
                    Reader.counter = Reader.counter - 1;
                    if (Reader.counter == 0) {
                        new TimeTable();
                    }
                }
            } catch (NumberFormatException var3) {
                JOptionPane.showMessageDialog((Component)null, "Введіть в поле число!");
            }

        }
    }
}
