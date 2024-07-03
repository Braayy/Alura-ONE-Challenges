package io.github.braayy.forum.features.reply;

import io.github.braayy.forum.dto.ErrorDTO;
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

@RestController
@RequestMapping("/replies")
@SecurityRequirement(name = "bearer-key")
public class ReplyController {

    private final ReplyService replyService;
    private final SecurityService securityService;

    public ReplyController(ReplyService replyService, SecurityService securityService) {
        this.replyService = replyService;
        this.securityService = securityService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ShowReplyDTO> create(
            @Valid @RequestBody CreateReplyDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Reply reply = this.replyService.create(dto, loggedInUser);

        URI uri = uriBuilder
                .path("/replies/{id}")
                .build(reply.getId());

        return ResponseEntity.created(uri)
                .body(new ShowReplyDTO(reply));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowReplyDTO> show(
        @PathVariable Long id
    ) {
        Reply reply = this.replyService.getById(id);

        return ResponseEntity.ok(new ShowReplyDTO(reply));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateReply(authentication, #id)")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateReplyDTO body
    ) {
        Reply reply = this.replyService.update(id, body);

        return ResponseEntity.ok(new ShowReplyDTO(reply));
    }

    @PutMapping("/{id}/solution")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canMarkReplyAsSolution(authentication, #id)")
    public ResponseEntity<?> markSolution(
        @PathVariable Long id,
        @Valid @RequestBody MarkReplyAsSolutionDTO body
    ) {
        Reply reply = this.replyService.markSolution(id, body.solution());

        return ResponseEntity.ok(new ShowReplyDTO(reply));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateReply(authentication, #id)")
    public ResponseEntity<?> delete(
        @PathVariable Long id
    ) {
        this.replyService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> list(
        @RequestParam(required = false) Long topic,
        @RequestParam(required = false) Long user,
        @PageableDefault Pageable pageable
    ) {
        if (topic != null) {
            Page<Reply> replies = this.replyService.getRepliesForTopic(topic, pageable);

            return ResponseEntity.ok(
                    replies
                            .map(ListReplyDTO::new)
            );
        }

        if (user != null) {
            Page<Reply> replies = this.replyService.getRepliesForUser(user, pageable);

            return ResponseEntity.ok(
                    replies
                            .map(ListReplyDTO::new)
            );
        }

        return ResponseEntity.badRequest()
                .body(new ErrorDTO("É necessário que seja passado ou um tópico ou usuário para a listagem!"));
    }

}
