package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public interface Mapper<T> {
    T doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz);

    boolean canMap(Class<?> clazz);
}