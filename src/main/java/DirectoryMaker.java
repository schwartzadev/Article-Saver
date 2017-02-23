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

    public static void saveToFile(ArrayList<String> names, String sectionTitle, ArrayList<String> links) {
        try {
            File outputfile = new File("directory.html");
            FileWriter fWriter;
            fWriter = new FileWriter(outputfile);
            PrintWriter pWriter = new PrintWriter(fWriter);
            pWriter.println("<html>\n" +
                    "<head>\n" +
                        "  <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n" +
                    "</head>\n" +
                    "<h1>File Directory</h1>\n" +
                    "<h2>" + sectionTitle + "</h2>\n" +
                    "<ul>");
            for (int i=0; i<names.size(); i++) {
                pWriter.println("  <li><a href=\"" + Vars.articles.get(i).getLink() + "\">" + Vars.articles.get(i).getTitle() + "</a></li>");
            }
            pWriter.println("</ul>\n" +
                    "</html>");
            pWriter.close();
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
            i.printStackTrace();
        }
    }
}
