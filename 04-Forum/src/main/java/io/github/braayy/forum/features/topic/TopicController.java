package io.github.braayy.forum.features.topic;

import io.github.braayy.forum.dto.ErrorDTO;
import io.github.braayy.forum.features.reply.ListReplyDTO;
import io.github.braayy.forum.features.reply.ReplyService;
import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserRole;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateTopicDTO body,
        UriComponentsBuilder uriBuilder
    ) {
        if (this.topicService.alreadyExists(body)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorDTO("Topico com mesmo titulo e mensagem já existente!"));
        }

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Topic topic = this.topicService.create(body, loggedInUser);

        URI uri = uriBuilder
            .path("/topics/{id}")
            .build(topic.getId());

        return ResponseEntity.created(uri)
                .body(new ShowTopicDTO(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTopicDTO> show(
        @PathVariable Long id
    ) {
        Topic topic = this.topicService.getById(id);

        return ResponseEntity.ok(new ShowTopicDTO(topic));
    }

    @GetMapping
    public ResponseEntity<Page<ListTopicDTO>> list(
        @PageableDefault Pageable pageable,
        @RequestParam(required = false) Long author,
        @RequestParam(required = false) Long course
    ) {
        Page<Topic> page = this.topicService.listAll(author, course, pageable);

        return ResponseEntity.ok(
            page
                .map(ListTopicDTO::new)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTopicDTO body
    ) {
        Topic topic = this.topicService.getById(id);
        var loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getRole() != UserRole.ADMIN && !topic.getAuthor().getId().equals(loggedInUser.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDTO("Você não tem permissão para modificar esse tópico!"));
        }

        Topic updatedTopic = this.topicService.update(topic, body);

        return ResponseEntity.ok(new ShowTopicDTO(updatedTopic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(
        @PathVariable Long id
    ) {
        Topic topic = this.topicService.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getRole() != UserRole.ADMIN && !topic.getAuthor().getId().equals(loggedInUser.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDTO("Você não tem permissão para deletar esse tópico!"));
        }

        this.topicService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
