import com.google.gson.*;
import java.io.*;


public class JsonParserDemo {

    public static void main(String[] args) throws IOException{
        String input = curl("http://www.npr.org/sections/thetwo-way/2017/02/21/516433342/homeland-security-outlines-new-rules-tightening-enforcement-of-immigration-law");
        input = input.substring(input.indexOf("{")); // removes info before actual JSON
        Gson gson = new Gson();
        Article s = gson.fromJson(input, Article.class);
        System.out.println(s);
        s.save();
    }

    /**
     * @usage parses article into JSON, from URL
     * @param link in form of a news article URL
     * @return parsed link as JSON
     * @throws IOException
     */
    public static String curl(String link) throws IOException {
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

class Article {
    private String title;
    private String author;
    private String date_published;
    private String domain;
    private String content;

    public Article(){
    }

    @Override
    public String toString() {
        return "Name: " + this.title + "\n" +
                "Author: " + this.author + "\n" +
                "Date: " + this.date_published + "\n" +
                "Source: " + this.domain;
    }

    /**
     * Prints article object to file
     * @throws IOException
     */
    public void save() throws IOException {
        File outputfile = new File (this.title.replaceAll(" ", "-") + ".html");
        FileWriter fWriter = new FileWriter (outputfile);
        PrintWriter pWriter = new PrintWriter (fWriter);
        pWriter.println ("<h1>" + title + "</h1>\n" +
                "<h2>" + author + " via " + domain + "</h2>" +
                "<h2>" + date_published.replaceAll("[a-zA-Z].*", "") + "</h2>" + // replaces datetime with just date
                content);
        pWriter.close();
    }
}