package annotations;

import converters.Converter;
import converters.TextConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSelector {

    String query();

    Class<? extends Converter> converter() default TextConverter.class;
}
