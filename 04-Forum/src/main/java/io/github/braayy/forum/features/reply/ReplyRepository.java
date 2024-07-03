package io.github.braayy.forum.features.reply;

import io.github.braayy.forum.features.topic.Topic;
import io.github.braayy.forum.features.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByTopic(Topic topic, Pageable pageable);

    Page<Reply> findAllByAuthor(User user, Pageable pageable);
}
