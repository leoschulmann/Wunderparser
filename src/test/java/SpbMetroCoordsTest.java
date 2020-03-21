import TestModels.SpbMetroWiki;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpbMetroCoordsTest {
    private static SpbMetroWiki metro;

    @BeforeAll
    static void setup() {
        Parser parser = Parser.getInstance();
        try {
             metro =
            parser.parse(SpbMetroWiki.class, new URL("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLinesSize() throws IOException {
        assertEquals(8, metro.lines.size());
    }

    @Test
    void testStationsSize() {
        assertEquals(70, metro.stations.entrySet().size());
    }

    @Test
    void testKupchino(){
        assertEquals("59°49′46″ с. ш.", metro.stations.get("Купчино").latitude);
    }

}