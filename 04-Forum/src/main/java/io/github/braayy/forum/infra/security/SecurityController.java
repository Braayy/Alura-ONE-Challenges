package io.github.braayy.forum.infra.security;

import io.github.braayy.forum.features.user.User;
import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public SecurityController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> login(
        @RequestBody @Valid LoginDTO body
    ) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.email(), body.password()));
        Optional<String> tokenOpt = this.tokenService.generateToken((User) authentication.getPrincipal());

        if (tokenOpt.isEmpty()) {
        	return ResponseEntity.badRequest()
        			.body(new AuthenticationFailedDTO("Email ou senha incorretos"));
        }

        return ResponseEntity.ok(new AuthenticationSuccessDTO(tokenOpt.get()));
    }

}
