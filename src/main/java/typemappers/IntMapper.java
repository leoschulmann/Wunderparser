package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class IntMapper implements Mapper<Integer> {
    @Override
    public Integer doMap(Elements elements, Type[] params, Class<?> clazz, Converter converter) {
        return (Integer) converter.convert(elements);
    }

    @Override
    public boolean canMap(Class clazz) {
        return clazz.getTypeName().equals("java.lang.Integer")
                || clazz.getTypeName().equals("int");
    }
}
