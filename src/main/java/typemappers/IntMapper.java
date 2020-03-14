package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class IntMapper implements Mapper<Integer> {
    @Override
    public Integer doMap(Elements elements, Type[] params, Class<?> clazz, Class<? extends Converter> conClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Converter converter = conClass.getConstructor().newInstance();
        return Integer.parseInt(converter.convert(elements));
    }

    @Override
    public boolean canMap(Class clazz) {
        return clazz.getTypeName().equals("java.lang.Integer")
                || clazz.getTypeName().equals("int");
    }
}
