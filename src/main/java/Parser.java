import annotations.FieldSelector;
import annotations.RootClassSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import typemappers.MapperFactory;
import typemappers.Util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
    final static Path HTMLcache = Paths.get("src/main/resources/webpage.html");
    private static Parser parser;

    private Parser() {
    }

    public <T> T parse(Class<T> rootClass, URL url) {
        try {
            Document doc = getDocument(url);
            T rootObject = rootClass.getConstructor().newInstance();
            if (!rootClass.isAnnotationPresent(RootClassSelector.class)) {
                return null;
            }
            String classSelector = rootClass.getAnnotation(RootClassSelector.class).query();
            Elements elements = doc.select(classSelector);
            Field[] fields = rootClass.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(FieldSelector.class)) {
                    continue;
                }
                String fieldSelector = field.getAnnotation(FieldSelector.class).query();
                Elements fieldElements = elements.select(fieldSelector);
                Class<?> aClass = field.getType();
                Type[] paramTypes = Util.checkParametrizedType(field);
                String mode = field.getAnnotation(FieldSelector.class).mode();
                Object argument = MapperFactory
                        .getMapper(aClass)
                        .doMap(fieldElements, paramTypes, aClass, mode);

                Util.setArgument(rootClass, rootObject, field, argument);
            }
            return rootObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document getDocument(URL url) throws IOException {
        if (!Files.exists(HTMLcache)) {
            String html = Jsoup.connect(url.toString()).maxBodySize(0).get().html();
            Files.write(HTMLcache, html.getBytes());
        }
        return Jsoup.parse(HTMLcache.toFile(), "UTF-8", url.toString());
    }

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
        }
        return parser;
    }

}
