package io.github.braayy.literalura.LiterAlura.service;

import io.github.braayy.literalura.LiterAlura.model.Author;
import io.github.braayy.literalura.LiterAlura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Optional<Author> getByName(String name) {
        return this.repository.findByNameContainingIgnoreCase(name);
    }

    public Author save(Author author) {
        return this.repository.save(author);
    }

    public List<Author> getAll() {
        return this.repository.findAll();
    }

    public List<Author> getAllAliveIn(int year) {
        return this.repository.findByBirthYearBeforeAndDeathYearAfter(year, year);
    }
}
