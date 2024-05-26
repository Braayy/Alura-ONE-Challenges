package io.github.braayy.literalura.LiterAlura.repository;

import io.github.braayy.literalura.LiterAlura.model.Author;
import io.github.braayy.literalura.LiterAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleContainingIgnoreCaseAndAuthor(String title, Author author);

    List<Book> findByLanguageIgnoreCase(String language);

    List<Book> findTop10ByOrderByDownloadsDesc();

}
