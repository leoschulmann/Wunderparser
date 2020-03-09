import model.SkillboxCourses;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException,
            ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        Parser parser = Parser.getInstance();
        SkillboxCourses sc = parser.parse(SkillboxCourses.class, new URL("https://skillbox.ru/"));
        System.out.println(sc);
    }
}
