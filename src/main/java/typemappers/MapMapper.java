package typemappers;

import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapMapper implements Mapper<Map<?, ?>> {
    @Override
    public Map<?, ?> doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, String mode) {
        Map<Object, Object> map = new HashMap<>();
        try {
            Class<?> keyClass = Class.forName(genTypes[0].getTypeName());
            Class<?> valClass = Class.forName(genTypes[1].getTypeName());
            Mapper<?> keyMapper = MapperFactory.getMapper(keyClass);
            Mapper<?> valueMapper = MapperFactory.getMapper(valClass);
            for (int i = 0; i < jsoupElmnts.size(); i += 2) {
                Object key = keyMapper.doMap(jsoupElmnts.eq(i), null, keyClass, mode);
                Object value = valueMapper.doMap(jsoupElmnts.eq(i + 1), null, valClass, mode);
                map.put(key, value);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
