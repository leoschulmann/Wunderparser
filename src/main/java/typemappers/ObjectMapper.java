package typemappers;

import annotations.ClassSelector;
import annotations.FieldSelector;
import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class ObjectMapper implements Mapper<Object> {
    @Override
    public Object doMap(Elements elems, Type[] types, Class<?> aClass, Converter converter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Object anObject = aClass.getConstructor().newInstance();
        for (Field aField : aClass.getDeclaredFields()) {
            if (!aField.isAnnotationPresent(FieldSelector.class)) {
                continue;
            }
            String fieldSelector = aField.getAnnotation(FieldSelector.class).query();
            Elements fieldElements = elems.select(fieldSelector);
            Type[] paramTypes = Util.checkParametrizedType(aField);
            Converter<?> fieldConverter = aField
                    .getAnnotation(FieldSelector.class)
                    .converter()
                    .getConstructor().newInstance();
            Object argument = MapperFactory
                    .getMapper(aField.getType())
                    .doMap(fieldElements, paramTypes, aField.getType(), fieldConverter);

            Util.setArgument(aClass, anObject, aField, argument);
        }
        return anObject;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.isAnnotationPresent(ClassSelector.class);
    }
}
