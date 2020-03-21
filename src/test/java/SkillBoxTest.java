import TestModels.SkillboxCourses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillBoxTest {
    private static SkillboxCourses sc;

    @BeforeAll
    static void setup() {
        Parser parser = Parser.getInstance();
        try {
            sc = parser.parse(SkillboxCourses.class, new URL("https://skillbox.ru/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCoursesQty() {
        assertEquals(16, sc.coursesObjs.size());
    }

    @Test
    void testTitle() {
        assertEquals("Курсы", sc.title);
    }
}