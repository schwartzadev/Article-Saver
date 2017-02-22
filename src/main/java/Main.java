import java.io.IOException;

/**
 * Created by andrew on 2/21/17.
 */
public class Main {
    public static void main(String[] args) {
        for (String s : GetLinks.googleLinkGetter("China Trump")) {
            Parser.Parse(s);
            System.out.println(s);
        }
    }
}
