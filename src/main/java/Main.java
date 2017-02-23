import java.util.ArrayList;

/**
 * Created by andrew on 2/21/17.
 * TODO remove images from articles
 * TODO remove duplicates from ArrayList
 * TODO fix time output from parser
 */
public class Main {
    public static String search = "sport";
    public static void main(String[] args) {
        for (String s : GetLinks.googleLinkGetter()) {
            Parser.Parse(s);
            System.out.println(s);
        }
        ArrayList<String> articles = DirectoryMaker.filter(DirectoryMaker.ls());
        System.out.println(articles);
        System.out.println(Vars.articleNames);
        DirectoryMaker.saveToFile(Vars.articleNames, search, articles);
    }
}
