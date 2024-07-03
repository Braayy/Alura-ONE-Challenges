package io.github.braayy.forum.features.course;

public record ShowCourseDTO(
    String name,
    String category
) {
    public ShowCourseDTO(Course course) {
        this(course.getName(), course.getCategory());
    }
}
