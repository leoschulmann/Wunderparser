package converters;

import org.jsoup.select.Elements;

public class StyleConverter implements Converter<String> {
    @Override
    public String convert(Elements elements) {
        return elements.eq(0).attr("style");
    }
}
