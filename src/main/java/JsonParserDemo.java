import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JsonParserDemo {

    public static void main(String[] args) throws IOException{
        String input = curl("http://www.npr.org/sections/thetwo-way/2017/02/21/516433342/homeland-security-outlines-new-rules-tightening-enforcement-of-immigration-law");
        input = input.substring(input.indexOf("{")); // removes info before actual JSON
        Gson gson = new Gson();
        Source s = gson.fromJson(input, Source.class);
        System.out.println(s);
    }

    /**
     * @usage parses article into JSON, from URL
     * @param link in form of a news article URL
     * @return parsed link as JSON
     * @throws IOException
     */
    public static String curl(String link) throws IOException {
        String curl = "curl -H \"x-api-key: key\" \"https://mercury.postlight.com/parser?url=" + link + "\"";
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

class Source{
    private String title;
    private String author;
    private String date_published;
    private String domain;

    public Source(){
    }

    @Override
    public String toString() {
        return "Name: " + this.title + "\n" +
                "Author: " + this.author + "\n" +
                "Date: " + this.date_published + "\n" +
                "Source: " + this.domain;
    }
}