package io.github.braayy.forum.features.reply;

public record CreateReplyDTO(
    String message,
    Long topicId
) {
}
