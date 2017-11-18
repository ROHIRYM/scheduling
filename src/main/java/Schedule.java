import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Schedule {
    private int numOfColumns;
    private int numOfLevels;
    private String[] levels;
    private ArrayList<Lesson[]> day;
    private JFrame jFrame;

    public Schedule(int numOfLevels, String[] levels, ArrayList<Lesson[]> day, String s) {
        this.numOfColumns = (int) Math
                .ceil(numOfLevels / 2);
        this.numOfLevels = numOfLevels;
        this.levels = levels;
        this.day = day;
        jFrame = new JFrame(s);
        jFrame.setLayout(new GridLayout(0, numOfColumns));
        fillLabels();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(140 * numOfColumns, 600);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }

    private void fillLabels() {
        for (int i = 0; i < numOfColumns; i++) {
            JLabel jLabel = new JLabel(levels[i]);
            jLabel.setBackground(Color.BLACK);
            jLabel.setForeground(Color.WHITE);
            jLabel.setOpaque(true);
            jFrame.add(jLabel);
        }
        for (Lesson[] lesson : day) {
            for (int i = 0; i < numOfColumns; i++) {
                JLabel jLabel = new JLabel(getSubject(lesson[i]));
                jLabel.setBackground(Color.WHITE);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
            for (int i = 0; i < numOfColumns; i++) {
                JLabel jLabel = new JLabel(getTeacher(lesson[i]));
                jLabel.setBackground(Color.lightGray);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
        }
        boolean isNumOfColumnsMore = 2 * numOfColumns > numOfLevels;
        for (int i = numOfColumns; i < numOfLevels; i++) {
            JLabel jLabel = new JLabel(levels[i]);
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
        for (Lesson[] lesson : day) {
            for (int i = numOfColumns; i < numOfLevels; i++) {
                JLabel jLabel = new JLabel(getSubject(lesson[i]));
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
            for (int i = numOfColumns; i < numOfLevels; i++) {
                JLabel jLabel = new JLabel(getTeacher(lesson[i]));
                jLabel.setBackground(Color.lightGray);
                jLabel.setForeground(Color.BLACK);
                jLabel.setOpaque(true);
                jFrame.add(jLabel);
            }
            if (isNumOfColumnsMore) {
                JLabel jLabel = new JLabel("");
                jLabel.setBackground(Color.lightGray);
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
}
