package io.github.braayy.forum.features.reply;

import jakarta.validation.constraints.NotNull;

public record MarkReplyAsSolutionDTO(
    @NotNull
    Boolean solution
) {
}
