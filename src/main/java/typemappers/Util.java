package typemappers;

import annotations.MapSelector;
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

    public static Object deserializeObject(Elements parentElements, Class<?> aClass) {
        try {
            Object anObject = aClass.getConstructor().newInstance();
            for (Field aField : aClass.getDeclaredFields()) {
                Elements[] fieldElements = null;
                Converter<?>[] fieldConverters = null;
                if (aField.isAnnotationPresent(Selector.class)) {
                    fieldElements = new Elements[] {parentElements.select(aField.getAnnotation(Selector.class).query())};
                    fieldConverters = new Converter<?>[] {aField.getAnnotation(Selector.class).converter().getConstructor().newInstance()};
                } else if (aField.isAnnotationPresent(MapSelector.class)) {
                    fieldElements = new Elements[2];
                    fieldElements[0] = parentElements.select(aField.getAnnotation(MapSelector.class).keyQuery());
                    fieldElements[1] = parentElements.select(aField.getAnnotation(MapSelector.class).valueQuery());
                    fieldConverters = new Converter<?>[2];
                    fieldConverters[0] = aField.getAnnotation(MapSelector.class).keyConverter().getConstructor().newInstance();
                    fieldConverters[1] = aField.getAnnotation(MapSelector.class).valueConverter().getConstructor().newInstance();
                } else {
                    continue;
                }
                Type[] paramTypes = Util.checkParametrizedType(aField);
                Mapper<?> mapper = MapperFactory.getMapper(aField.getType());
                Object argument = mapper.doMap(fieldElements, paramTypes,
                        aField.getType(), fieldConverters);
                Util.setArgument(aClass, anObject, aField, argument);
            }
            return anObject;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
