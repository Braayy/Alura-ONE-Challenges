package io.github.braayy.forum.features.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTopicDTO(
    @NotBlank
    @Size(max = 200)
    String title,

    @NotBlank
    String message,

    @NotNull
    Long courseId
) {
}
