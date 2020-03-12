package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class SetMapper implements Mapper<Set<?>>{
    @Override
    public Set<?> doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, String mode) {
        Set<Object> set = new HashSet<>();
        for (int i = 0; i < jsoupElmnts.size(); i++) {
            try {
                Class<?> aClass = Class.forName(genTypes[0].getTypeName());
                Object argument = MapperFactory
                        .getMapper(aClass)
                        .doMap(jsoupElmnts.eq(i), null, aClass, mode);
                set.add(argument);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return Set.class.isAssignableFrom(clazz);
    }
}
