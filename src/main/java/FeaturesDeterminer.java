import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class FeaturesDeterminer {
    private String[] levels;
    private Reader[] readers;

    public FeaturesDeterminer() {
        DataBaseReader dataBaseReader = new DataBaseReader();
        HashMap<String, ArrayList<String>> subjectsOfLevels = dataBaseReader.getSubjectsOfLevels();
        HashMap<String, ArrayList<String>> teachersOfSubjects = dataBaseReader.getTeachersOfSubjects();
        int allLevels = subjectsOfLevels.size();
        Reader.setCounter(allLevels);
        this.levels = new String[allLevels];
        this.readers = new Reader[allLevels];
        this.createWindows(subjectsOfLevels, teachersOfSubjects);
    }

    private void createWindows(HashMap<String, ArrayList<String>> subjectsOfLevels, HashMap<String, ArrayList<String>> teachersOfSubjects) {
        int i = 0;

        for(Iterator iterator = subjectsOfLevels.entrySet().iterator(); iterator.hasNext(); ++i) {
            Entry<String, ArrayList<String>> pair = (Entry)iterator.next();
            ArrayList<String> subjects = (ArrayList)pair.getValue();
            HashMap<String, ArrayList<String>> teachersOfCertainSubjects = new HashMap();
            Iterator var9 = subjects.iterator();

            while(var9.hasNext()) {
                String subject = (String)var9.next();
                if (teachersOfSubjects.containsKey(subject)) {
                    teachersOfCertainSubjects.put(subject, (ArrayList)teachersOfSubjects.get(subject));
                }
            }

            this.levels[i] = (String)pair.getKey();
            this.readers[i] = new Reader(this.levels[i], teachersOfCertainSubjects, 7);
        }

    }

    public String[] getLevels() {
        return this.levels;
    }

    public Reader[] getReaders() {
        return this.readers;
    }
}
