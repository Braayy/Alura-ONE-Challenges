package io.github.braayy.forum.features.topic;

import jakarta.validation.constraints.Size;

public record UpdateTopicDTO(
    @Size(min = 1, max = 200)
    String title,

    @Size(min = 1)
    String message,

    Long courseId
) {
}
