package typemappers;

import converters.Converter;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapMapper implements Mapper<Map<?, ?>> {
    @Override
    public Map<?, ?> doMap(Elements[] jsoupElmnts, Type[] genTypes, Class<?> clazz, Converter[] converter) {
        try {
            if (jsoupElmnts[0].size() != jsoupElmnts[1].size()) {
                throw new IllegalArgumentException("Key and value mismatch");
            }
            Class<?> keyClass = Class.forName(genTypes[0].getTypeName());
            Class<?> valClass = Class.forName(genTypes[1].getTypeName());
            Mapper<?> keyMapper = MapperFactory.getMapper(keyClass);
            Mapper<?> valueMapper = MapperFactory.getMapper(valClass);
            Map<Object, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < jsoupElmnts[0].size(); i++) {
                Object key = keyMapper.doMap(new Elements[] {jsoupElmnts[0].eq(i)}, null, keyClass, new Converter[] {converter[0]});
                Object value = valueMapper.doMap(new Elements[] {jsoupElmnts[1].eq(i)}, null, keyClass, new Converter[] {converter[1]});
                map.put(key, value);
            }
            return map;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
