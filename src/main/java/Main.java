import model.SkillboxCourses;
import model.SpbMetroWiki;

import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
        Parser parser = Parser.getInstance();
//        SkillboxCourses sc = parser.parse(SkillboxCourses.class, new URL("https://skillbox.ru/"));
//        System.out.println(sc);
        SpbMetroWiki spbMetroWiki = parser.parse(SpbMetroWiki.class, new URL("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0"));
        System.out.println();
    }
}
