package TestModels;

import annotations.Selector;

import java.util.List;
import java.util.stream.Collectors;

@Selector(query = "div.container > section:nth-child(4)", root = true)
public class SkillboxCourses {
    @Selector(query = "header > h2")
    public String title;

    @Selector(query = "header > p")
    public String description;

    @Selector(query = "div > a > div")
    public List<SkillboxCourse> coursesObjs;

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
