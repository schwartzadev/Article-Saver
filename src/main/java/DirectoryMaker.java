import java.io.*;
import java.util.*;

/**
 * Created by andrew on 2/22/17.
 */
public class DirectoryMaker {
    /**
     * @usage ls passes the "ls" command line argument through the command line, and saves the returned file names to an ArrayList
     * @return names of files in doc/ as a set
     */
    public static ArrayList<String> ls() {
        try {
            String curl = "cd doc && ls";
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ArrayList<String> arr = new ArrayList<String>();
            while (true) {
                arr.add(r.readLine());
                if (r.readLine() == null) {
                    break;
                }
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // satisfies return requirement but wil never be triggered
    }

    /**
     * @usage filter takes in an ArrayList and returns the same Set, with select values removed. These include null values
     *        and any values whose length is < 5 since they cannot be a .html file.
     * @param arr the ArrayList to be filtered
     * @return
     */
    public static ArrayList<String> filter(ArrayList<String> arr) {
        Collection<String> removeCandidates = new LinkedList<String>();
        for(String element : arr)
            if(element == null || element.length() < 5) {
                removeCandidates.add(element);
            }
        arr.removeAll(removeCandidates);
        return arr;
    }

    public static void saveToFile(ArrayList<String> names, String sectionTitle) {
        try {
            File outputfile = new File("directory.html");
            FileWriter fWriter;
            fWriter = new FileWriter(outputfile);
            PrintWriter pWriter = new PrintWriter(fWriter);
            pWriter.println("<h1>File Directory</h1>\n" +
                    "<h2>" + sectionTitle + "</h2>" +
                    "<style type=\"text/css\">body{margin:30px auto;max-width:700px;line-height:1.4;font-size:1.3em;color:#354247;padding:0px 10px}a{color:#1355A0}h1,h2,h3{line-height:1;margin: 1em 0 0 0}</style>" +
                    "<ul>");
            for (String str : names) {
                pWriter.println("<li>" + str + "</li></br>");
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
