package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public interface Mapper<T> {
    //'mode' is used to pass a specific method for Jsoup to invoke on a field
    T doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, String mode);

    boolean canMap(Class<?> clazz);
}