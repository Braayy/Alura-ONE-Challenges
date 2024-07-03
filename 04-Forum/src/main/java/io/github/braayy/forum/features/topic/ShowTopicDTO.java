package io.github.braayy.forum.features.topic;

public record ShowTopicDTO(
    String title,
    String message,
    Long authorId,
    Long courseId
) {
    public ShowTopicDTO(Topic topic) {
        this(topic.getTitle(), topic.getMessage(), topic.getAuthor().getId(), topic.getCourse().getId());
    }
}
