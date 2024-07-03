package io.github.braayy.forum.features.user;

import io.github.braayy.forum.dto.ErrorDTO;
import io.github.braayy.forum.infra.security.SecurityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ShowUserDTO> register(
        @Valid @RequestBody RegisterUserDTO body,
        UriComponentsBuilder uriBuilder
    ) {
        User user = this.userService.register(body);

        URI uri = uriBuilder
            .path("/users/{id}")
            .build(user.getId());

        return ResponseEntity.created(uri)
            .body(new ShowUserDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowUserDTO> show(
        @PathVariable Long id
    ) {
        User user = this.userService.getById(id);

        return ResponseEntity.ok(new ShowUserDTO(user));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateUser(authentication, #id)")
    public ResponseEntity<?> edit(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserDTO body
    ) {
        User user = this.userService.update(id, body);

        return ResponseEntity.ok(new ShowUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canUpdateUser(authentication, #id)")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
