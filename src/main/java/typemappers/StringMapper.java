package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class StringMapper implements Mapper<String> {
    @Override
    public String doMap(Elements elements, Type[] types, Class<?> aClass, Converter converter) {
        return (String) converter.convert(elements);
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.getTypeName().equals("java.lang.String");
    }
}
