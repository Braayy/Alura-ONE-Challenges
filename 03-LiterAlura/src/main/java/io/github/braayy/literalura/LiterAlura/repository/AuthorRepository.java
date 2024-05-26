package io.github.braayy.literalura.LiterAlura.repository;

import io.github.braayy.literalura.LiterAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameContainingIgnoreCase(String name);

    List<Author> findByBirthYearBeforeAndDeathYearAfter(int birthYear, int deathYear);
}
