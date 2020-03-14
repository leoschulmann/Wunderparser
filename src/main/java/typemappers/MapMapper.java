package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapMapper implements Mapper<Map<?, ?>> {
    @Override
    public Map<?, ?> doMap(Elements jsoupElmnts, Type[] genTypes, Class<?> clazz, Class<? extends Converter> conClass) throws Exception {
        Map<Object, Object> map = new LinkedHashMap<>();
        Class<?> keyClass = Class.forName(genTypes[0].getTypeName());
        Class<?> valClass = Class.forName(genTypes[1].getTypeName());
        Mapper<?> keyMapper = MapperFactory.getMapper(keyClass);
        Mapper<?> valueMapper = MapperFactory.getMapper(valClass);
        for (int i = 0; i < jsoupElmnts.size(); i += 2) {
            Object key = keyMapper.doMap(jsoupElmnts.eq(i), null, keyClass, conClass);
            Object value = valueMapper.doMap(jsoupElmnts.eq(i + 1), null, valClass, conClass);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
