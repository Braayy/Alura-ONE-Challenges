package io.github.braayy.forum.features.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsByTitleAndMessage(String title, String message);

    @Query("""
        SELECT t
        FROM Topic t
        WHERE (:authorId is null OR t.author.id = :authorId) AND (:courseId is null OR t.course.id = :courseId)
    """)
    Page<Topic> findAllByAuthorAndCourse(Long authorId, Long courseId, Pageable pageable);

}
