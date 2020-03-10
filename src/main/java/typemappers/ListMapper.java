package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListMapper implements Mapper<List<?>, Elements, Type[], Class<?>> {
    @Override
    public List<?> doMap(Elements elements, Type[] types, Class<?> aClass) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            try {
                Class<?> clazz = Class.forName(types[0].getTypeName());
                Object argument = MapperFactory
                        .chooseMapper(clazz)
                        .doMap(elements.eq(i), null, clazz);
                list.add(argument);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }
}
