package annotations;

import converters.Converter;
import converters.TextConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapSelector {
    String keyQuery();
    String valueQuery();
    Class<? extends Converter> keyConverter() default TextConverter.class;
    Class<? extends Converter> valueConverter() default TextConverter.class;

}
