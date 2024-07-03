package io.github.braayy.forum.features.topic;

public record ListTopicDTO(
    Long id,
    String title,
    String message,
    Long authorId,
    Long courseId
) {
    public ListTopicDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getAuthor().getId(), topic.getCourse().getId());
    }
}
