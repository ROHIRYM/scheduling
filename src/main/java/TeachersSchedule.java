import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TeachersSchedule {

    private int numOfColumns;
    private int numOfLvls;
    private HashMap<String, ArrayList<Lesson>> day;
    private JFrame jFrame;
    private ArrayList<String> allTeachers;

    public TeachersSchedule(ArrayList<Lesson[]> thisDay , String s, ArrayList<String> allTeachers) {
        numOfLvls = 0;
        this.day = groupByTeachers(thisDay, allTeachers);
        this.numOfColumns = (int) Math
                .ceil(day.size() / 2);
        this.allTeachers = allTeachers;
        jFrame = new JFrame(s);
        jFrame.setLayout(new GridLayout(0, numOfColumns));
        fillLabels();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(120 * numOfColumns, 600);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }

    private HashMap<String, ArrayList<Lesson>> groupByTeachers(ArrayList<Lesson[]> thisDay, ArrayList<String> allTeachers) {
        HashMap<String, ArrayList<Lesson>> result = new HashMap<String, ArrayList<Lesson>>();
        for (String t : allTeachers) {
            result.put(t, new ArrayList<Lesson>());
            System.out.println("==>" + t);
        }
        numOfLvls = thisDay.size();
        for (Lesson[] les : thisDay) {
            ArrayList<String> teachers = new ArrayList<String>();
            for (Lesson le : les) {
                System.out.println("-->"+le);
                if (le == null) {
                    continue;
                }
                result.get(le.getTeacher()).add(new Lesson(le.getLevel(), le.getSubject()));
                teachers.add(le.getTeacher());
            }
            for (String t : allTeachers) {
                if (!teachers.contains(t)) {
                    result.get(t).add(new Lesson("", ""));
                }
            }
        }
        return result;
    }

    private void fillLabels() {
        for (int i = 0; i < numOfColumns; i++) {
            JLabel jLabel = new JLabel(allTeachers.get(i));
            jLabel.setBackground(Color.BLACK);
            jLabel.setForeground(Color.WHITE);
            jLabel.setOpaque(true);
            jFrame.add(jLabel);
        }
        for (int j = 0; j < numOfLvls; j++) {
            for (int i = 0; i < numOfColumns; i++) {
                String t = allTeachers.get(i);
                Lesson l = day.get(t).get(j);
                JLabel jLabel = new JLabel(makeSubstring(l.getSubject()) + " - " + l.getLevel());
                jLabel.setBackground(Color.WHITE);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
        }
        boolean isNumOfColumnsMore = 2 * numOfColumns > day.size();
        for (int i = numOfColumns; i < day.size(); i++) {
            JLabel jLabel = new JLabel(allTeachers.get(i));
            jLabel.setBackground(Color.BLACK);
            jLabel.setForeground(Color.WHITE);
            jLabel.setOpaque(true);
            jFrame.add(jLabel);
        }
        if (isNumOfColumnsMore) {
            JLabel jLabel = new JLabel("");
            jLabel.setBackground(Color.BLACK);
            jLabel.setForeground(Color.WHITE);
            jLabel.setOpaque(true);
            jFrame.add(jLabel);
        }
        for (int j = 0; j < numOfLvls; j++) {
            for (int i = numOfColumns; i < day.size(); i++) {
                String t = allTeachers.get(i);
                Lesson l = day.get(t).get(j);
                JLabel jLabel = new JLabel(makeSubstring(l.getSubject()) + " - " + l.getLevel());
                jLabel.setBackground(Color.WHITE);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
            if (isNumOfColumnsMore) {
                JLabel jLabel = new JLabel("");
                jLabel.setBackground(Color.WHITE);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
        }
    }

    private String getSubject(Lesson lesson) {
        if (lesson == null) {
            return "";
        } else {
            return lesson.getSubject();
        }
    }

    private String getTeacher(Lesson lesson) {
        if (lesson == null) {
            return "";
        } else {
            return lesson.getTeacher();
        }
    }

    private String makeSubstring(String s) {
        return s.length() < 13 ? s : s.substring(0, 13);
    }


}
