package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class StringMapper implements Mapper<String> {
    @Override
    public String doMap(Elements elements, Type[] types, Class<?> aClass, Class<? extends Converter> conClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Converter converter = conClass.getConstructor().newInstance();
        return converter.convert(elements);
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return clazz.getTypeName().equals("java.lang.String");
    }
}
