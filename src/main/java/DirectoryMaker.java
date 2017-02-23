import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by andrew on 2/22/17.
 */
public class DirectoryMaker {
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

    public static void saveToFile(Set<String> s) {
        try {
            File outputfile = new File("directory.html");
            FileWriter fWriter;
            fWriter = new FileWriter(outputfile);
            PrintWriter pWriter = new PrintWriter(fWriter);
            pWriter.println("<h1>File Directory</h1>\n" +
                    "<style type=\"text/css\">body{margin:30px auto;max-width:700px;line-height:1.4;font-size:1.3em;color:#354247;padding:0px 10px}a{color:#1355A0}h1,h2,h3{line-height:1;margin: 1em 0 0 0}</style>");
            for (String str : s) {
                pWriter.println(str + "</br>");
            }
            pWriter.close();
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
            i.printStackTrace();
        }
    }
}
