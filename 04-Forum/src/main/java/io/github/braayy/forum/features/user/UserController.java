package io.github.braayy.forum.features.user;

import io.github.braayy.forum.dto.ErrorDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<?> edit(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserDTO body
    ) {
        var loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getRole() != UserRole.ADMIN && !id.equals(loggedInUser.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDTO("Você não tem permissão para modificar esse usuário!"));
        }

        User user = this.userService.update(id, body);
        return ResponseEntity.ok(new ShowUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var loggedInUser = (User) auth.getPrincipal();
        if (loggedInUser.getRole() != UserRole.ADMIN && !id.equals(loggedInUser.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDTO("Você não tem permissão para deletar esse usuário!"));
        }

        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
