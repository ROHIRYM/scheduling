public class Lesson {
    private String level;
    private String teacher;
    private String subject;

    public Lesson(String level, String subject) {
        this.level = level;
        this.teacher = "";
        this.subject = subject;
    }

    public Lesson(String level, String teacher, String subject) {
        this.level = level;
        this.teacher = teacher;
        this.subject = subject;
    }

    public String getLevel() {
        return this.level;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public String getSubject() {
        return this.subject;
    }

    public boolean equals(Object o) {
        Lesson anotherLesson = (Lesson)o;
        return this.getLevel().equals(anotherLesson.getLevel()) && this.getTeacher().equals(anotherLesson.getTeacher());
    }

    public String toString() {
        return "l=" + this.level + ", t=" + this.teacher + ", s=" + this.subject;
    }
}
