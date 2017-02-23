import com.google.gson.*;
import java.io.*;


public class Parser {

    public static void Parse(String link) {
        String input;
        try {
            input = curl(link);
            input = input.substring(input.indexOf("{")); // removes info before actual JSON
            Gson gson = new Gson();
            Article s = gson.fromJson(input, Article.class);
            System.out.println(s);
            s.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        linksCount.linksCount++;
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
        return "[" + linksCount.linksCount + "/" + linksTotal.linksTotal + "}\n\t" +
                this.title;
    }

    /**
     * Prints article object to html file
     * @throws IOException
     */
    public void save() {
        try {
            File outputfile = new File("doc/" + this.title.replaceAll("[^a-zA-Z0-9]", "-").replaceAll("(-)\\1+", "-") + ".html");
            FileWriter fWriter = null;
            fWriter = new FileWriter(outputfile);
            PrintWriter pWriter = new PrintWriter(fWriter);
            pWriter.println("<h1>" + title + "</h1>\n" +
                    "<style type=\"text/css\">body{margin:30px auto;max-width:700px;line-height:1.4;font-size:1.3em;color:#354247;padding:0px 10px}a{color:#1355A0}h1,h2,h3{line-height:1;margin: 1em 0 0 0}</style>" +
                    "<h2>" + author + " via " + domain + "</h2>" +
                    "<h2>" + date_published/*.replaceAll("[a-zA-Z].*", "")*/ + "</h2>" + // replaces datetime with just date
                    content);
            pWriter.close();
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
            i.printStackTrace();
        }
    }
}