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

    @FieldSelector(query = "div > a > div")
    List<SkillboxCourse> coursesObjs;

    public SkillboxCourses() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoursesObjs(List<SkillboxCourse> coursesObjs) {
        this.coursesObjs = coursesObjs;
    }

    @Override
    public String toString() {
        return "SkillboxCourses {" + "\n" +
                "title = " + title + "\n" +
                "description = " + description + "\n" +
                "courses = " + coursesObjs
                .stream()
                .map(e -> e.toString())
                .collect(Collectors.joining("\n-----------------\n"));
    }
}
