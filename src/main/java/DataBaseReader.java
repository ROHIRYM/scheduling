import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseReader {
    private final String URL = "jdbc:mysql://localhost:3306/school";
    private final String USERNAME = "root";
    private final String PASSWORD = "кщще";
    private HashMap<String, ArrayList<String>> subjectsOfLevels;
    private HashMap<String, ArrayList<String>> teachersOfSubjects;

    public HashMap<String, ArrayList<String>> getSubjectsOfLevels() {
        return this.subjectsOfLevels;
    }

    public HashMap<String, ArrayList<String>> getTeachersOfSubjects() {
        return this.teachersOfSubjects;
    }

    public DataBaseReader() {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            System.out.println("1");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("2");
            Statement statement = connection.createStatement();
            ResultSet levelResultSet = statement.executeQuery("SELECT levels.name, subjects.name FROM levels INNER JOIN programs ON levels.id=programs.level_id INNER JOIN subjects ON subjects.id=programs.subject_id;");
            this.subjectsOfLevels = this.fillSubjectsOfLevels(levelResultSet);
            ResultSet teacherResultSet = statement.executeQuery("SELECT teachers.surname, teachers.name, teachers.second_name, subjects.name FROM teachers INNER JOIN competences ON teachers.id=competences.teacher_id_ INNER JOIN subjects ON subjects.id=competences.subject_id_;");
            this.teachersOfSubjects = this.fillTeachersOfSubjects(teacherResultSet);
            connection.close();
        } catch (SQLException e) {
            System.out.println("I am in catch");
            System.out.println(e);
        }

    }

    private HashMap<String, ArrayList<String>> fillSubjectsOfLevels(ResultSet resultSet) throws SQLException {
        HashMap result = new HashMap();

        while(resultSet.next()) {
            String levelName = resultSet.getString("levels.name");
            String subjectName = resultSet.getString("subjects.name");
            if (result.containsKey(levelName)) {
                ((ArrayList)result.get(levelName)).add(subjectName);
            } else {
                ArrayList<String> temp = new ArrayList();
                temp.add(subjectName);
                result.put(levelName, temp);
            }
        }

        return result;
    }

    private HashMap<String, ArrayList<String>> fillTeachersOfSubjects(ResultSet resultSet) throws SQLException {
        HashMap result = new HashMap();

        while(resultSet.next()) {
            String teacherSurname = resultSet.getString("teachers.surname");
            String teacherName = resultSet.getString("teachers.name");
            String teacherSecondName = resultSet.getString("teachers.second_name");
            String teacher = teacherSurname + " " + teacherName.charAt(0) + ". " + teacherSecondName.charAt(0) + ".";
            String subjectName = resultSet.getString("subjects.name");
            if (result.containsKey(subjectName)) {
                ((ArrayList)result.get(subjectName)).add(teacher);
            } else {
                ArrayList<String> temp = new ArrayList();
                temp.add(teacher);
                result.put(subjectName, temp);
            }
        }

        return result;
    }
}