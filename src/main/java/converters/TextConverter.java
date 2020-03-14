package converters;

import org.jsoup.select.Elements;

public class TextConverter implements Converter<String> {
    @Override
    public String convert(Elements elements) {
        return elements.eq(0).text();
    }
}
