/**
 * Created by andrew on 2/21/17.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Saver {
    public static void main(String[] args) throws IOException, StringIndexOutOfBoundsException {
        String output = curl("http://www.npr.org/2017/02/15/515425370/trump-labor-pick-andrew-puzders-nomination-appears-in-jeopardy");
        String title = parse(output, "{\"title\":\"", "\",\"content\":", 10);
        String content = parse(output, "\",\"content\":", "\",\"author\":", 13);
        String author = parse(output, "\",\"author\":", "\",\"date_published\":\"", 12);
        String source = parse(output, ",\"domain\":\"", "\",\"excerpt\":\"", 11);
        //String date = parse(output, "\"date_published\":\"", "\",\"", 10);
        //System.out.println(date);
        System.out.println(output);

        content = content.replace("class=\\\"","class=\""); // remove backslash from beginning of class names
        content = content.replace("\\\">","\">"); // remove backslash from end of class names
        content = content.replace("\\n",""); // remove extra "\n" from content
        content = content.replaceAll("<figure id=?.*?</figure>", ""); // remove all images
        // System.out.println(title);
        writeToFile(content, title, author, source);

    }
    public static String parse(String original, String begin, String end, int num) {
        return original.substring(original.indexOf(begin) + num, original.indexOf(end)); // parse title
    }

    public static boolean writeToFile(String content, String title, String author, String source) throws IOException { // writes the content to a file
        File outputfile = new File (title + ".html");
        FileWriter fWriter = new FileWriter (outputfile);
        PrintWriter pWriter = new PrintWriter (fWriter);
        pWriter.println ("<h1>" + title + "</h1>");
        pWriter.println ("<h2>" + author + " via " + source + "</h2>");
        pWriter.println (content);
        pWriter.close();
        System.out.println("done");
        return true;
    }

    public static String curl(String link) throws IOException { // writes the content to a file
        String curl = "curl -H \"x-api-key: JpHSuA0sDNPt4w1tOlWTwTSGt8HMRpUPmyNynbb3\" \"https://mercury.postlight.com/parser?url=" + link + "\"";
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        int linenum = 0;
        while (true) {
            linenum++;
            line = r.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        return sb.toString();
    }

}
