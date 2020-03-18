package typemappers;

import annotations.Selector;
import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Util {
    public static Type[] checkParametrizedType(Field field) {
        if (field.getType() == field.getGenericType()) {
            return null;
        }
        return ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    }

    public static void setArgument(Class<?> objectClass, Object object, Field
            field, Object argument) {
        try {
            String setterName = "set"
                    + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1);
            Method setter = objectClass.getMethod(setterName, field.getType());
            setter.invoke(object, argument);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getObject(Elements elems, Class<?> aClass) {
        try {
            Object anObject = aClass.getConstructor().newInstance();
            for (Field aField : aClass.getDeclaredFields()) {
                if (!aField.isAnnotationPresent(Selector.class)) {
                    continue;
                }
                Elements fieldElements = getElements(elems, aField);
                Type[] paramTypes = Util.checkParametrizedType(aField);
                Converter<?> fieldConverter = getFieldConverter(aField);
                Mapper<?> mapper = MapperFactory.getMapper(aField.getType());
                Object argument = mapper.doMap(fieldElements, paramTypes,
                        aField.getType(), fieldConverter);
                Util.setArgument(aClass, anObject, aField, argument);
            }
            return anObject;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Elements getElements(Elements elems, Field aField) {
        String fieldSelector = aField.getAnnotation(Selector.class).query();
        return elems.select(fieldSelector);
    }

    private static Converter<?> getFieldConverter(Field aField) {
        try {
            return aField.getAnnotation(Selector.class)
                    .converter().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
