package typemappers;

import annotations.Selector;
import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class ObjectMapper implements Mapper<Object> {
    @Override
    public Object doMap(Elements elems, Type[] types, Class<?> aClass, Converter converter) {
        return Util.getObject(elems, aClass);
    }

    @Override
    public boolean canMap(Class<?> aClass) {
        return aClass.isAnnotationPresent(Selector.class) &&
                aClass.getAnnotation(Selector.class).entity();
    }
}
