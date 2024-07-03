package io.github.braayy.forum.infra;

import io.github.braayy.forum.dto.ErrorDTO;
import io.github.braayy.forum.infra.security.AuthorizationFailedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestControllerAdvice
public class ExceptionBeautifier {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> genericError(Exception exception) {
        exception.printStackTrace();

        return ResponseEntity.badRequest()
                .body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException exception) {
        List<ValidationErrorDTO> errors = exception.getFieldErrors().stream()
            .map(ValidationErrorDTO::new)
            .toList();

        return ResponseEntity.badRequest()
            .body(errors);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<?> authorization(AuthorizationFailedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentials() {
        return ResponseEntity.badRequest()
            .body(new ErrorDTO("Email ou senha incorretos"));
    }

    public record ValidationErrorDTO(
        String field,
        String reason
    ) {
        public ValidationErrorDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
