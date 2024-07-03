package io.github.braayy.forum.features.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
    @Size(min = 1, max = 100)
    String name,

    @Email
    @Size(min = 1, max = 100)
    String email,

    @Size(min = 1)
    String password
) {
}
