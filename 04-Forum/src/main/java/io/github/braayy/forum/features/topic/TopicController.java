package io.github.braayy.forum.features.topic;

import io.github.braayy.forum.dto.ErrorDTO;
import io.github.braayy.forum.features.reply.ListReplyDTO;
import io.github.braayy.forum.features.reply.ReplyService;
import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserRole;
import io.github.braayy.forum.infra.security.SecurityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    private final TopicService topicService;
    private final SecurityService securityService;

    public TopicController(TopicService topicService, SecurityService securityService) {
        this.topicService = topicService;
        this.securityService = securityService;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateTopic(authentication, #id)")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTopicDTO body
    ) {
        Topic topic = this.topicService.update(id, body);

        return ResponseEntity.ok(new ShowTopicDTO(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateTopic(authentication, #id)")
    public ResponseEntity<?> delete(
        @PathVariable Long id
    ) {
        this.topicService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
