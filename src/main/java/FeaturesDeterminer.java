import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeaturesDeterminer {
    private String[] levels;
    private Reader[] readers;

    public FeaturesDeterminer() {
        DataBaseReader dataBaseReader = new DataBaseReader();
        HashMap<String, ArrayList<String>> subjectsOfLevels = dataBaseReader
                .getSubjectsOfLevels();
        HashMap<String, ArrayList<String>> teachersOfSubjects = dataBaseReader
                .getTeachersOfSubjects();
        int allLevels = subjectsOfLevels.size();
        Reader.setCounter(allLevels);
        levels = new String[allLevels];
        readers = new Reader[allLevels];
        createWindows(subjectsOfLevels, teachersOfSubjects);
    }

    private void createWindows(
            HashMap<String, ArrayList<String>> subjectsOfLevels,
            HashMap<String, ArrayList<String>> teachersOfSubjects) {
        int i = 0;
        Iterator<Map.Entry<String, ArrayList<String>>> iterator = subjectsOfLevels
                .entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<String>> pair = iterator.next();
            ArrayList<String> subjects = pair.getValue();
            HashMap<String, ArrayList<String>> teachersOfCertainSubjects = new HashMap<String, ArrayList<String>>();
            for (String subject : subjects) {
                if (teachersOfSubjects.containsKey(subject)) {
                    teachersOfCertainSubjects.put(subject, teachersOfSubjects.get(subject));
                }
            }
            levels[i] = pair.getKey();
            readers[i] = new Reader(levels[i], teachersOfCertainSubjects, 7);
            i++;
        }
    }

    public String[] getLevels() {
        return levels;
    }

    public Reader[] getReaders() {
        return readers;
    }
}
