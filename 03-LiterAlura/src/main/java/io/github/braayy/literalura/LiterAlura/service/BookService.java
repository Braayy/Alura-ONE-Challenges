package io.github.braayy.literalura.LiterAlura.service;

import io.github.braayy.literalura.LiterAlura.model.Author;
import io.github.braayy.literalura.LiterAlura.model.Book;
import io.github.braayy.literalura.LiterAlura.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAll() {
        return this.repository.findAll();
    }

    public List<Book> getAllByLanguage(String language) {
        return this.repository.findByLanguageIgnoreCase(language);
    }

    public Optional<Book> getLivro(Author author, String title) {
        return this.repository.findByTitleContainingIgnoreCaseAndAuthor(title, author);
    }

    public List<Book> getTop10() {
        return this.repository.findTop10ByOrderByDownloadsDesc();
    }

    public Book save(Book book) {
        return this.repository.save(book);
    }
}
