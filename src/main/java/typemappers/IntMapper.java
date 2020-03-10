package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;

public class IntMapper implements Mapper<Integer, Elements, Type[], Class<?>> {
    @Override
    public Integer doMap(Elements elements, Type[] params, Class<?> clazz) {
        return Integer.parseInt(elements.eq(0).text());
    }

    @Override
    public boolean canMap(Class clazz) {
        return clazz.getTypeName().equals("java.lang.Integer")
                || clazz.getTypeName().equals("int");
    }
}
