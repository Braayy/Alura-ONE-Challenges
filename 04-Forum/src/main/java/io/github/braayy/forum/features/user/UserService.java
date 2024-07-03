package io.github.braayy.forum.features.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado"));
    }

    public User register(RegisterUserDTO dto) {
        String encodedPassword = this.passwordEncoder.encode(dto.password());

        User user = User.builder()
            .name(dto.name())
            .email(dto.email())
            .password(encodedPassword)
            .role(UserRole.MEMBER)
            .build();

        return this.userRepository.save(user);
    }

    public User getById(Long id) {
        return this.userRepository.getReferenceById(id);
    }

    public User update(Long userId, UpdateUserDTO dto) {
        User user = this.userRepository.getReferenceById(userId);

        if (dto.name() != null) {
            user.setName(dto.name());
        }

        if (dto.email() != null) {
            user.setEmail(dto.email());
        }

        if (dto.password() != null) {
            String encodedPassword = this.passwordEncoder.encode(dto.password());
            user.setPassword(encodedPassword);
        }

        return user;
    }

    public void delete(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
