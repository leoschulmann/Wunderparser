package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListMapper implements Mapper<List<?>> {
    @Override
    public List<?> doMap(Elements elements, Type[] types, Class<?> aClass, Class<? extends Converter> conClass) throws Exception {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Class<?> clazz = Class.forName(types[0].getTypeName());
            Object argument = MapperFactory
                    .getMapper(clazz)
                    .doMap(elements.eq(i), null, clazz, conClass);
            list.add(argument);
        }
        return list;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }
}
