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

        // add more
    }

    static public <T> Mapper<T> chooseMapper(Class<?> clazz) {
        for (Mapper<?> mapper : mappers) {
            if (mapper.canMap(clazz)) {
                return (Mapper<T>) mapper;
            }
        }
        return null;
    }
}
