package io.github.braayy.forum.features.reply;

import io.github.braayy.forum.features.topic.Topic;
import io.github.braayy.forum.features.topic.TopicRepository;
import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Reply create(CreateReplyDTO dto, User loggedInUser) {
        Topic topic = this.topicRepository.getReferenceById(dto.topicId());

        Reply reply = Reply.builder()
                .message(dto.message())
                .solution(false)
                .author(loggedInUser)
                .topic(topic)
                .build();

        return this.replyRepository.save(reply);
    }

    public Reply getById(Long replyId) {
        return this.replyRepository.getReferenceById(replyId);
    }

    public Reply update(Reply reply, UpdateReplyDTO dto) {
        if (dto.message() != null) {
            reply.setMessage(dto.message());
        }

        return reply;
    }

    public Reply markSolution(Reply reply, boolean solution) {
        reply.setSolution(solution);

        return reply;
    }

    public void deleteById(Long replyId) {
        this.replyRepository.deleteById(replyId);
    }

    public Page<Reply> getRepliesForTopic(Long topicId, Pageable pageable) {
        Topic topic = this.topicRepository.getReferenceById(topicId);

        return this.replyRepository.findAllByTopic(topic, pageable);
    }

    public Page<Reply> getRepliesForUser(Long userId, Pageable pageable) {
        User user = this.userRepository.getReferenceById(userId);

        return this.replyRepository.findAllByAuthor(user, pageable);
    }
}
