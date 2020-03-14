package converters;

import org.jsoup.select.Elements;

public class HrefConverter implements Converter {

    @Override
    public String convert(Elements elements) {
        return elements.eq(0).attr("href");
    }
}
