package io.github.braayy.forum.features.topic;

import io.github.braayy.forum.features.course.Course;
import io.github.braayy.forum.features.course.CourseRepository;
import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Topic create(CreateTopicDTO dto, User loggedInUser) {
        Course course = this.courseRepository.getReferenceById(dto.courseId());

        Topic topic = Topic.builder()
                .title(dto.title())
                .message(dto.message())
                .author(loggedInUser)
                .course(course)
                .createdAt(LocalDate.now())
                .status(TopicStatus.OPEN)
                .build();

        return this.topicRepository.save(topic);
    }

    public boolean alreadyExists(CreateTopicDTO dto) {
        return this.topicRepository.existsByTitleAndMessage(dto.title(), dto.message());
    }

    public Topic getById(Long topicId) {
        return this.topicRepository.getReferenceById(topicId);
    }

    public Page<Topic> listAll(Long authorId, Long courseId, Pageable pageable) {
        return this.topicRepository.findAllByAuthorAndCourse(authorId, courseId, pageable);
    }

    public Topic update(Long topicId, UpdateTopicDTO dto) {
        Topic topic = this.topicRepository.getReferenceById(topicId);

        if (dto.title() != null) {
            topic.setTitle(dto.title());
        }

        if (dto.message() != null) {
            topic.setMessage(dto.message());
        }

        if (dto.courseId() != null) {
            Course course = this.courseRepository.getReferenceById(dto.courseId());
            topic.setCourse(course);
        }

        return topic;
    }

    public void deleteById(Long topicId) {
        this.topicRepository.deleteById(topicId);
    }
}
