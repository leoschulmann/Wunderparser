import annotations.Selector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import typemappers.Util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
    final static String CACHE_LOCATION = "src/main/resources/cache/";
    private static Parser parser;
    private static Logger logger;

    private Parser() {
    }

    public <T> T parse(Class<T> rootClass, URL url) throws IOException {
        if (!rootClass.getAnnotation(Selector.class).root()) {
            return null;
        }
            Document html = getDocument(url);
            String rootquery = rootClass.getAnnotation(Selector.class).query();
            Elements rootElements = html.select(rootquery);
            return (T) Util.deserializeObject(rootElements, rootClass);
    }

    private Document getDocument(URL url) throws IOException {
        String hash = String.valueOf(url.hashCode());
        Path pathToCache = Paths.get(CACHE_LOCATION + hash + ".html");
        if (!Files.exists(pathToCache)) {
            String html = Jsoup.connect(url.toString()).maxBodySize(0).get().html();
            Files.write(pathToCache, html.getBytes());
        }
        return Jsoup.parse(pathToCache.toFile(), "UTF-8", url.toString());
    }

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
            logger = LogManager.getRootLogger();
        }
        return parser;
    }
}
