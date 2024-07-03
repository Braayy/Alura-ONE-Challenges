package io.github.braayy.forum.features.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(
    @NotBlank
    @Size(min = 1, max = 100)
    String name,

    @NotBlank
    @Email
    @Size(min = 1, max = 100)
    String email,

    @NotBlank
    String password
) {
}
