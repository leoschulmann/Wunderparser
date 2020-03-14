package model;

import annotations.ClassSelector;
import annotations.FieldSelector;
import converters.StyleConverter;
import converters.TitleConverter;

@ClassSelector
public class SpbMetroLine {
    @FieldSelector(query = "td:nth-child(1)", converter = StyleConverter.class)
    String color;
    @FieldSelector(query = "td:nth-child(1) > a", converter = TitleConverter.class)
    String name;

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpbMetroLine that = (SpbMetroLine) o;

        if (color != null ? !color.equals(that.color) : that.color != null) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpbMetroLine{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}'+"\n";
    }
}
