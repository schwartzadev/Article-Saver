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
    public static void main(String[] args) {


    }
    public static Set<String> ls() {
        // save all files in directory to a set
        try {
            String curl = "cd doc && ls";
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            Set<String> set = new HashSet<>();
            String line;
            int linenum = 0;
            while (true) {
                linenum++;
                set.add(r.readLine());
                if (r.readLine() == null) {
                    break;
                }
            }
            return set;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<String> sort(Set<String> set) {
        Collection<String> removeCandidates = new LinkedList<String>();
        for(String element : set)
            if(element == null || element.length()<5) {
                removeCandidates.add(element);
            }
        set.removeAll(removeCandidates);
        return set;
    }
}
