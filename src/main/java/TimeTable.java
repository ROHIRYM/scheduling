import java.util.ArrayList;

public class TimeTable {
    private int numOfLevels;
    private String[] levels;
    private ArrayList<Lesson[]> monday;
    private ArrayList<Lesson[]> tuesday;
    private ArrayList<Lesson[]> wednesday;
    private ArrayList<Lesson[]> thursday;
    private ArrayList<Lesson[]> friday;
    private ArrayList<String> allTeachers;

    public TimeTable(ArrayList<String> allTeachers) {
        System.out.println(allTeachers.size());
        this.allTeachers = allTeachers;
        FeaturesDeterminer featuresDeterminer = Main.featuresDeterminer;
        this.levels = featuresDeterminer.getLevels();
        Reader[] readers = featuresDeterminer.getReaders();
        ArrayList<ArrayList<Lesson>> lessons = new ArrayList();
        this.numOfLevels = readers.length;
        int sumOfLessons = 0;

        for(int i = 0; i < this.numOfLevels; ++i) {
            ArrayList<String> finalSubjects = readers[i].getFinalSubjects();
            ArrayList<Integer> finalHours = readers[i].getFinalHours();
            ArrayList<String> finalTeachers = readers[i].getFinalTeachers();
            ArrayList<Lesson> oneLevel = new ArrayList();
            this.fillLessons(oneLevel, this.levels[i], finalSubjects, finalHours, finalTeachers);
            lessons.add(oneLevel);
            sumOfLessons += oneLevel.size();
        }

        Lesson[][] matrix = new Lesson[sumOfLessons][this.numOfLevels];
        int len = this.createMatrix(matrix, lessons);
        this.monday = new ArrayList();
        this.tuesday = new ArrayList();
        this.wednesday = new ArrayList();
        this.thursday = new ArrayList();
        this.friday = new ArrayList();
        this.fillDays(matrix, len);
        this.showSchedule();
        this.showTeachersSchedule();
    }

    private void fillLessons(ArrayList<Lesson> lessons, String level, ArrayList<String> finalSubjects, ArrayList<Integer> finalHours, ArrayList<String> finalTeachers) {
        for(int i = 0; i < finalSubjects.size(); ++i) {
            String tempSubject = (String)finalSubjects.get(i);
            int tempHour = ((Integer)finalHours.get(i)).intValue();
            String tempTeacher = (String)finalTeachers.get(i);

            for(int j = 0; j < tempHour; ++j) {
                lessons.add(new Lesson(level, tempTeacher, tempSubject));
            }
        }

    }

    private int createMatrix(Lesson[][] matrix, ArrayList<ArrayList<Lesson>> lessons) {
        int maxLen = 0;

        for(int c = 0; c < this.numOfLevels; ++c) {
            ArrayList<Lesson> oneLevel = (ArrayList)lessons.get(c);
            int len = 0;

            for(int r = 0; r < oneLevel.size(); ++r) {
                Lesson tempLesson = (Lesson)oneLevel.get(r);
                len = this.add2Matrix(matrix, tempLesson, c);
            }

            if (len > maxLen) {
                maxLen = len;
            }
        }

        return maxLen;
    }

    private int add2Matrix(Lesson[][] matrix, Lesson tempLesson, int c) {
        for(int r = 0; r < matrix.length; ++r) {
            if (matrix[r][c] == null && this.isSetPossible(matrix, tempLesson, c, r)) {
                matrix[r][c] = tempLesson;
                return r + 1;
            }
        }

        return 0;
    }

    private boolean isSetPossible(Lesson[][] matrix, Lesson tempLesson, int c, int r) {
        for(int i = 0; i < c; ++i) {
            if (matrix[r][i] != null && matrix[r][i].getTeacher().equals(tempLesson.getTeacher())) {
                return false;
            }
        }

        return true;
    }

    private void fillDays(Lesson[][] matrix, int len) {
        int j = 0;

        for(int cnt = 0; j < len; ++cnt) {
            if (cnt > 4) {
                cnt = 0;
            }

            switch(cnt) {
                case 0:
                    this.monday.add(matrix[j]);
                    break;
                case 1:
                    this.tuesday.add(matrix[j]);
                    break;
                case 2:
                    this.wednesday.add(matrix[j]);
                    break;
                case 3:
                    this.thursday.add(matrix[j]);
                    break;
                case 4:
                    this.friday.add(matrix[j]);
            }

            ++j;
        }

    }

    private void showSchedule() {
        new Schedule(this.numOfLevels, this.levels, this.friday, "П'ятниця");
        new Schedule(this.numOfLevels, this.levels, this.thursday, "Четвер");
        new Schedule(this.numOfLevels, this.levels, this.wednesday, "Середа");
        new Schedule(this.numOfLevels, this.levels, this.tuesday, "Вівторок");
        new Schedule(this.numOfLevels, this.levels, this.monday, "Понеділок");
    }

    private void showTeachersSchedule() {
        new TeachersSchedule(this.friday, "П'ятниця", allTeachers);
        new TeachersSchedule(this.thursday, "Четвер", allTeachers);
        new TeachersSchedule(this.wednesday, "Середа", allTeachers);
        new TeachersSchedule(this.tuesday, "Вівторок", allTeachers);
        new TeachersSchedule(this.monday, "Понеділок", allTeachers);
    }
}
