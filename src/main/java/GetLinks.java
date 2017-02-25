import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;



public class GetLinks {

    /**
     * Takes in a search term, returns list of URLs to be parsed
     * @return list of sources to be parsed
     * @throws IOException
     */
    public static ArrayList<String> googleLinkGetter() {
        String url = "https://www.google.com/search?tbm=nws&q=" + Main.search;
        Document doc = null;
        try { doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");

        ArrayList<String> result = new ArrayList<String>();
        for (Element link : links) {
            if (String.valueOf(link.attr("abs:href")).matches("https://www.google.com/url\\?q=http.*")) { //remove non news links
                result.add(String.valueOf(link.attr("abs:href")));
            }
        }
        for (int i = 0; i<result.size(); i++) {
            result.set(i, ((result.get(i).substring(29).replaceAll("&sa=.*", "")))); //remove first 29 characters: "https://www.google.com/url?q=", and remove Google News ending
        }
        result = new ArrayList<String>(new LinkedHashSet<String>(result)); // removes duplicates since LinkedHashSet cannot contain duplicates
        Vars.linksTotal = result.size();
        Vars.linksCount = 1;
        return result;
    }
}
