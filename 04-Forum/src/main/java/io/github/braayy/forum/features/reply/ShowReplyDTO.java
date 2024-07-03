package io.github.braayy.forum.features.reply;

public record ShowReplyDTO(
    String message,
    Boolean solution,
    Long topicId,
    Long authorId
) {
    public ShowReplyDTO(Reply reply) {
        this(reply.getMessage(), reply.getSolution(), reply.getTopic().getId(), reply.getAuthor().getId());
    }
}
