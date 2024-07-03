package io.github.braayy.forum.features.course;

public record ListCourseDTO(
    Long id,
    String name,
    String category
) {
    public ListCourseDTO(Course course) {
        this(course.getId(), course.getName(), course.getCategory());
    }
}
