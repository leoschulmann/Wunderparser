package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class StringMapper implements Mapper<String, Elements, Type[], Class<?>> {
    @Override
    public String doMap(Elements elements, Type[] types, Class<?> aClass) {
        return elements.eq(0).text();
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.getTypeName().equals("java.lang.String");
    }
}
