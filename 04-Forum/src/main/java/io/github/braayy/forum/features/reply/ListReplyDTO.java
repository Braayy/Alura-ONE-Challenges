package io.github.braayy.forum.features.reply;

public record ListReplyDTO(
    Long id,
    String message,
    Boolean solution,
    Long topicId,
    Long authorId
) {
    public ListReplyDTO(Reply reply) {
        this(reply.getId(), reply.getMessage(), reply.getSolution(), reply.getTopic().getId(), reply.getAuthor().getId());
    }
}
