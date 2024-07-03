package io.github.braayy.forum.infra.security;

import io.github.braayy.forum.features.reply.Reply;
import io.github.braayy.forum.features.reply.ReplyService;
import io.github.braayy.forum.features.topic.Topic;
import io.github.braayy.forum.features.topic.TopicService;
import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final ReplyService replyService;
    private final TopicService topicService;

    public SecurityService(ReplyService replyService, TopicService topicService) {
        this.replyService = replyService;
        this.topicService = topicService;
    }

    public boolean canUpdateUser(Authentication authentication, Long userId) {
        User loggedInUser = (User) authentication.getPrincipal();
        Long loggedInUserId = loggedInUser.getId();

        return loggedInUserId.equals(userId);
    }

    public boolean canUpdateTopic(Authentication authentication, Long topicId) {
        Topic topic = this.topicService.getById(topicId);
        Long authorId = topic.getAuthor().getId();

        User loggedInUser = (User) authentication.getPrincipal();
        Long userId = loggedInUser.getId();

        return authorId.equals(userId);
    }

    public boolean canUpdateReply(Authentication authentication, Long replyId) {
        Reply reply = this.replyService.getById(replyId);
        Long authorId = reply.getAuthor().getId();

        User loggedInUser = (User) authentication.getPrincipal();
        Long userId = loggedInUser.getId();

        return authorId.equals(userId);
    }

    public boolean canMarkReplyAsSolution(Authentication authentication, Long replyId) {
        Reply reply = this.replyService.getById(replyId);
        Long topicAuthorId = reply.getTopic().getAuthor().getId();

        User loggedInUser = (User) authentication.getPrincipal();
        Long userId = loggedInUser.getId();

        return topicAuthorId.equals(userId);
    }

}
