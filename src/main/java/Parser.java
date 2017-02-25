import com.google.gson.*;
import java.io.*;


public class Parser {

    public static void Parse(String link) {
        String input;
        try {
            input = curl(link);
            input = input.substring(input.indexOf("{")); // removes info before actual JSON
            Gson gson = new Gson();
            Article a = gson.fromJson(input, Article.class);
            Vars.articles.add(a);
            System.out.println(a);
            a.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Vars.linksCount++;
    }

    /**
     * @usage parses article into JSON, from URL
     * @param link in form of a news article URL
     * @return parsed link as JSON
     * @throws IOException
     */
    public static String curl(String link) throws IOException {
        String curl = "curl -H \"x-api-key: " + Api.key + "\" \"https://mercury.postlight.com/parser?url=" + link + "\"";
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
    private String link;

    public Article(){
    }

    @Override
    public String toString() {
        return "[" + Vars.linksCount + "/" + Vars.linksTotal + "]\n\t" +
                this.title;
    }

    /**
     * Prints article object to html file
     * @throws IOException
     */
    public void save() {
        if (date_published!=null) {
            date_published = date_published.substring(0, 10); //fix date
        } else {
            date_published = "No Date";
        }

        try {
            Vars.articleNames.add(this.title);
            String link = "doc/" + this.title.replaceAll("[^a-zA-Z0-9]", "-").replaceAll("(-)\\1+", "-") + ".html";
            setLink(link);
            File outputfile = new File(link);
            FileWriter fWriter = null;
            fWriter = new FileWriter(outputfile);
            PrintWriter pWriter = new PrintWriter(fWriter);
            pWriter.println("<html>\n" +
                    "<head>\n" +
                    "  <link rel=\"stylesheet\" type=\"text/css\" href=\"res/styles.css\">\n" +
                    "</head>\n" +
                    "<h1>" + title + "</h1>");
            if (this.author!=null) {
                pWriter.println("<h2>" + author + " via " + domain + "</h2>");
            } else {
                pWriter.println("<h2>" + domain + "</h2>");
            }
            pWriter.println("<h2>" + date_published + "</h2>\n" +
                    content);
            pWriter.close();
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public String getLink() {
        return link;
    }
    public String getTitle() {
        return title;
    }
    public void setLink(String link) {
        this.link = link;
    }
}