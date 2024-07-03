package io.github.braayy.forum.infra.security;

import io.github.braayy.forum.features.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf((conf) -> conf.disable())
            .sessionManagement((conf) -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((conf) -> conf
                .requestMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/users/**",
                    "/courses",
                    "/courses/**",
                    "/topics",
                    "/topics/**",
                    "/replies",
                    "/replies/**"
                ).permitAll()

                .requestMatchers(HttpMethod.POST, "/courses").hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/courses/**").hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole(UserRole.ADMIN.name())

                .anyRequest().authenticated()
            )
            .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
