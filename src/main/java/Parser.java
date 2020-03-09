import annotations.ClassSelector;
import annotations.FieldSelector;
import annotations.RootClassSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    final static Path HTMLcache = Paths.get("src/main/resources/webpage.html");
    private static Parser parser;

    private Parser() {
    }
    public <T> T parse(Class<T> rootClass, URL url)
    {
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
                Type[] paramTypes = checkParametrizedType(field);
                Object argument = conjure(fieldElements, aClass, paramTypes);
                setArgument(rootClass, rootObject, field, argument);
            }
            return rootObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object conjure(Elements elements, Class<?> aClass, Type[] paramTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //parsing simple types
        if (aClass.getTypeName().equals("java.lang.String") ||
                aClass.getTypeName().equals("int")) {
            return conjurePrimitive(aClass, elements);
        }

        //parsing annotated class
        if (aClass.isAnnotationPresent(ClassSelector.class)) {
            return conjureObject(elements, aClass);
        }

        //parsing list
        if (List.class.isAssignableFrom(aClass)) {
            return conjureListOfObjects(elements, paramTypes);
        }

        //parsing map
        if (Map.class.isAssignableFrom(aClass)) {
            //todo make Maps great again
        }

        //annotated but not matching
        System.out.println(aClass.getTypeName() + " was not created");
        return null;
    }

    private Type[] checkParametrizedType(Field field) {
        if (field.getType() == field.getGenericType()) {
            return null;
        }
        return ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    }

    private Object conjurePrimitive(Class<?> aClass, Elements elements) {
        switch (aClass.getTypeName()) {
            case "java.lang.String":
                return elements.get(0).text();
            case "int":
                return Integer.parseInt(elements.get(0).text());
            // more primitive types may be required
            default:
                return null;
        }
    }

    private Object conjureObject(Elements elements, Class<?> aClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object anObject = aClass.getConstructor().newInstance();
        for (Field aField : aClass.getDeclaredFields()) {
            if (!aField.isAnnotationPresent(FieldSelector.class)) {
                continue;
            }
            String fieldSelector = aField.getAnnotation(FieldSelector.class).query();
            Elements fieldElements = elements.select(fieldSelector);
            Type[] paramTypes = checkParametrizedType(aField);
            Object argument = conjure(fieldElements, aField.getType(), paramTypes);
            setArgument(aClass, anObject, aField, argument);
        }
        return anObject;
    }

    private List<?> conjureListOfObjects(Elements elements, Type[] paramTypes) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            try {
                Class<?> clazz = Class.forName(paramTypes[0].getTypeName());
                Object conjuredObject = conjure(elements.eq(i), clazz, null);
                list.add(conjuredObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private Object conjureMapOfObjects(Elements elements, Field field) {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        return null;
    }

    private void setArgument(Class<?> objectClass, Object object, Field
            field, Object argument) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String setterName = "set"
                + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        Method setter = objectClass.getMethod(setterName, field.getType());
        setter.invoke(object, argument);
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
