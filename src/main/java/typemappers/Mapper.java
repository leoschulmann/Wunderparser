package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface Mapper<T> {
    T doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz);

    boolean canMap(Class<?> clazz);

     static Type[] checkParametrizedType(Field field) {
        if (field.getType() == field.getGenericType()) {
            return null;
        }
        return ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    }

     static void setArgument(Class<?> objectClass, Object object, Field
             field, Object argument) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String setterName = "set"
                + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        Method setter = objectClass.getMethod(setterName, field.getType());
        setter.invoke(object, argument);
    }
}
