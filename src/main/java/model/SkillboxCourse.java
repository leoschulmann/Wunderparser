package model;

import annotations.ClassSelector;
import annotations.FieldSelector;

@ClassSelector(query = "div > a > div")
public class SkillboxCourse {
    @FieldSelector(query = "b")
    String name;

    @FieldSelector(query = "span.tag.line-card__tag.hover-card__text")
    String type;

    @FieldSelector(query = "span.duration.line-card__duration.hover-card__text")
    String length;

    public SkillboxCourse() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return name + "\n\t" + type + "\n\t" + length;
    }
}
