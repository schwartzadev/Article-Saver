import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by andrew on 2/22/17.
 */
public class IndexMaker {
    /**
     * @usage ls passes the "ls" command line argument through the command line, and saves the returned file names to a set
     * @return names of files in doc/ as a set
     */
    public static Set<String> ls() {
        try {
            String curl = "cd doc && ls";
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            Set<String> set = new HashSet<>();
            while (true) {
                set.add(r.readLine());
                if (r.readLine() == null) {
                    break;
                }
            }
            return set;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // satisfies return requirement but wil never be triggered
    }

    /**
     * @usage filter takes in a Set and returns the same Set, with select values removed. These include null values
     *        and any values whose length is < 5 since they cannot be a .html file.
     * @param set the set to be filtered
     * @return
     */
    public static Set<String> filter(Set<String> set) {
        Collection<String> removeCandidates = new LinkedList<String>();
        for(String element : set)
            if(element == null || element.length() < 5) {
                removeCandidates.add(element);
            }
        set.removeAll(removeCandidates);
        return set;
    }
}
