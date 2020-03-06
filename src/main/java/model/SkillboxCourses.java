package model;

import annotations.FieldSelector;
import annotations.RootClassSelector;

import java.util.List;
import java.util.stream.Collectors;

@RootClassSelector(query = "div.container > section:nth-child(4)")
public class SkillboxCourses {
    @FieldSelector(query = "header > h2")
    String title;

    @FieldSelector(query = "header > p")
    String description;

    @FieldSelector(query = "div > a > div > b")
    List<String> courses;

    public SkillboxCourses() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "SkillboxCourses {" + "\n" +
                "title = " + title + "\n" +
                "description = " + description + "\n" +
                "courses = " + courses.stream().collect(Collectors.joining("\n"));
    }
}
