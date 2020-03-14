package typemappers;

import java.util.ArrayList;
import java.util.List;

public class MapperFactory {
    static private List<Mapper<?>> mappers = new ArrayList<>();

    private MapperFactory() {
    }

    static {
        mappers.add(new IntMapper());
        mappers.add(new StringMapper());
        mappers.add(new ObjectMapper());
        mappers.add(new ListMapper());
        mappers.add(new SetMapper());
        mappers.add(new MapMapper());
    }

    public static <T> Mapper<T> getMapper(Class<?> clazz) {
        for (Mapper<?> mapper : mappers) {
            if (mapper.canMap(clazz)) {
                return (Mapper<T>) mapper;
            }
        }
        return null;
    }

     public static void addCustomMapper(Mapper<?> mapper) {
        mappers.add(mapper);
    }
}
