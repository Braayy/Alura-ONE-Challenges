package io.github.braayy.forum.features.course;

import jakarta.validation.constraints.Size;

public record UpdateCourseDTO(
    @Size(min = 1, max = 100)
    String name,

    @Size(min = 1, max = 100)
    String category
) {
}
