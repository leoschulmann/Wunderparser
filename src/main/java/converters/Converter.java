package converters;

import org.jsoup.select.Elements;

public interface Converter<T> {
    T convert(Elements elements);
}
