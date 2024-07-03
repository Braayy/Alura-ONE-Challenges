package io.github.braayy.forum.features.user;

public record ShowUserDTO(
    String name,
    String email
) {
    public ShowUserDTO(User user) {
        this(user.getName(), user.getEmail());
    }
}
