package io.github.braayy.forum.features.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum UserRole {
    MEMBER,
    ADMIN,
    ;

    private final SimpleGrantedAuthority authority;

    UserRole() {
        this.authority = new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
