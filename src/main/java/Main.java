/**
 * Created by andrew on 2/21/17.
 * TODO remove images from articles
 * TODO edit spacing on articles for better reading
 * TODO make a directory file for downloaded articles
 * TODO fix titles--remove all chars but [a-zA-Z] and '-'
 */
public class Main {
    public static String search = "China";
    public static void main(String[] args) {
        /*for (String s : GetLinks.googleLinkGetter()) {
            Parser.Parse(s);
            System.out.println(s);
        }*/
        System.out.println(DirectoryMaker.filter(DirectoryMaker.ls()));
        DirectoryMaker.saveToFile(Vars.articleNames, search);
    }
}
