package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public interface Mapper<T> {
    T doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, Converter converter);

    boolean canMap(Class<?> clazz);
}