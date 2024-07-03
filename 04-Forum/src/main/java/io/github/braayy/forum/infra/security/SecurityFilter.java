package io.github.braayy.forum.infra.security;

import io.github.braayy.forum.features.user.User;
import io.github.braayy.forum.features.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getRequestToken(request).ifPresent((token) -> {
            Optional<String> subjectOpt = this.tokenService.getSubjectFromToken(token);
            if (subjectOpt.isEmpty()) {
                throw new AuthorizationFailedException("Token invalido");
            }

            Optional<User> userOpt = this.userRepository.findByEmail(subjectOpt.get());
            if (userOpt.isEmpty()) {
                throw new AuthorizationFailedException("Usuário invalido");
            }

            User user = userOpt.get();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getRequestToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
            .map((value) -> value.replace("Bearer ", ""));
    }
}
