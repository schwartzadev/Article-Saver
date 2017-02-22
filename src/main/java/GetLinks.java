import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetLinks {

    /**
     * Takes in a search term, returns list of URLs to be parsed
     * @param search - query for Google News
     * @return list of sources to be parsed
     * @throws IOException
     */
    public static List<String> googleLinkGetter(String search) {
        String url = "https://www.google.com/search?tbm=nws&q=" + search;
        Document doc = null;
        try { doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");

        List<String> result = new ArrayList<String>();
        for (Element link : links) {
            if (String.valueOf(link.attr("abs:href")).matches("https://www.google.com/url\\?q=http.*")) {
                result.add(String.valueOf(link.attr("abs:href")));
            }
        }
        System.out.println(Arrays.toString(result.toArray()));
        return result;
    }

}
