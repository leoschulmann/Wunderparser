package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public interface Mapper<T> {
    T doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, Class<? extends Converter> conClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, Exception;

    boolean canMap(Class<?> clazz);
}