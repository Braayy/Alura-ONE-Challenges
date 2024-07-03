package io.github.braayy.forum.features.reply;

import jakarta.validation.constraints.Size;

public record UpdateReplyDTO(
    @Size(min = 1)
    String message
) {
}
