package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class StringMapper implements Mapper<String> {
    @Override
    public String doMap(Elements elements, Type[] types, Class<?> aClass, String mode) {
        switch (mode) {
            case "text":
                return elements.eq(0).text();
            case "href":
                return elements.eq(0).attr("href");
            case "style":
                return elements.eq(0).attr("style");
            case "title":
                return elements.eq(0).attr("title");
            //more JSoup commands might come in handy
            default:
                return null;
        }
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.getTypeName().equals("java.lang.String");
    }
}
