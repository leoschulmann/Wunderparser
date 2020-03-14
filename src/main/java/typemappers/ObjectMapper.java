package typemappers;

import annotations.ClassSelector;
import annotations.FieldSelector;
import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ObjectMapper implements Mapper<Object> {
    @Override
    public Object doMap(Elements elems, Type[] types, Class<?> aClass, Class<? extends Converter> conClass) {
        try {
            Object anObject = aClass.getConstructor().newInstance();
            for (Field aField : aClass.getDeclaredFields()) {
                if (!aField.isAnnotationPresent(FieldSelector.class)) {
                    continue;
                }
                String fieldSelector = aField.getAnnotation(FieldSelector.class).query();
                Elements fieldElements = elems.select(fieldSelector);
                Type[] paramTypes = Util.checkParametrizedType(aField);
                Class<? extends Converter> fieldConClass = aField.getAnnotation(FieldSelector.class).converter();
                Object argument = MapperFactory
                        .getMapper(aField.getType())
                        .doMap(fieldElements, paramTypes, aField.getType(), fieldConClass);

                Util.setArgument(aClass, anObject, aField, argument);
            }
            return anObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.isAnnotationPresent(ClassSelector.class);
    }
}
