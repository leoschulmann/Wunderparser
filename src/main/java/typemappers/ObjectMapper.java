package typemappers;

import annotations.ClassSelector;
import annotations.FieldSelector;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ObjectMapper implements Mapper<Object> {
    @Override
    public Object doMap(Elements elems, Type[] types, Class<?> aClass, String mode) {
        try {
            Object anObject = aClass.getConstructor().newInstance();
            for (Field aField : aClass.getDeclaredFields()) {
                if (!aField.isAnnotationPresent(FieldSelector.class)) {
                    continue;
                }
                String fieldSelector = aField.getAnnotation(FieldSelector.class).query();
                Elements fieldElements = elems.select(fieldSelector);
                Type[] paramTypes = Util.checkParametrizedType(aField);
                String aMode = aField.getAnnotation(FieldSelector.class).mode();
                Object argument = MapperFactory
                        .getMapper(aField.getType())
                        .doMap(fieldElements, paramTypes, aField.getType(), aMode);

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
