package model;

import annotations.ClassSelector;
import annotations.FieldSelector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ClassSelector
public class Coordinate {
    @FieldSelector(query = "a")
    String latitude;

    //intentionally no annotation
    String longitude;

    //some dirty hack to map two strings from one found string
    public void setLatitude(String string) {
        Pattern p = Pattern.compile("[0-9]{2}\\W[0-9]{2}\\W[0-9]{2}\\W\\s[сювзшд. ]{5}");
        Matcher m = p.matcher(string);
        m.find();
        this.latitude = m.group(0);
        m.find();
        this.longitude = m.group(0);
    }
}
