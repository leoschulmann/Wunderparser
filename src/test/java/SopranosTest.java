import TestModels.SopranosShow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SopranosTest {
    private static SopranosShow show;

    @BeforeAll
    static void start() {
        Parser p = Parser.getInstance();
        try {
            show = p.parse(SopranosShow.class, new URL("https://en.wikipedia.org/wiki/The_Sopranos"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCharacters() {
        assertEquals("Tony Soprano", show.getCharacters().get(0));
    }

    @Test
    void testEpisodes() {
        assertEquals("Made in America",
                show.getSeasons().stream()
                        .flatMap(s -> s.getEpisodes().stream())
                        .reduce((first, second) -> second).orElse(""));
    }
}
