package io.github.braayy.forum.features.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCourseDTO(
    @NotBlank
    @Size(max = 100)
    String name,

    @NotBlank
    @Size(max = 100)
    String category
) {
}
