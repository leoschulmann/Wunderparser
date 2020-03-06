import annotations.FieldSelector;
import annotations.RootClassSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    final static Path HTMLcache = Paths.get("src/main/resources/webpage.html");

    private static Parser parser;

    private Parser() {
    }

    public Object parse(String className, URL url) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Document doc = getDocument(url);
        Class<?> rootClass = Class.forName(className);
        Object rootObject = rootClass.getConstructor().newInstance();

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
            Object argument = css2oop(fieldElements, field.getType());
            setArgument(rootClass, rootObject, field, argument);
        }
        System.out.println();
        return rootObject;
    }

    private static Object css2oop(Elements elements, Class<?> aClass) {
        switch (aClass.getTypeName()) {
            case "java.lang.String":
                return elements.text();
            case "int":
                return Integer.parseInt(elements.text());
                // more primitive types may be required
        }
        if (List.class.isAssignableFrom(aClass)) {
            System.out.println();
            return elements.stream().map(Element::text).collect(Collectors.toList());
        }


        return null;
    }

    private void setArgument(Class<?> rootClass, Object rootObject, Field field, Object argument) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String setterName = "set"
                + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        Method setter = rootClass.getMethod(setterName, field.getType());
        setter.invoke(rootObject, argument);
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

//    private static Object parseField(Class<?> clazz, Field f, Elements elements) {
//        //for each field get selector, find data...
//        String query = f.getAnnotation(FieldSelector.class).query();
//        Elements data = elements.select(query);
//        String preParseMethodName = "preParse"
//                + f.getName().substring(0, 1).toUpperCase()
//                + f.getName().substring(1);
//        try {
//            //...  pass all found data to special method in class
//            return clazz.getMethod(preParseMethodName).invoke(data);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        //... if no special method in class defined return object???
//        return null;
//    }
}
