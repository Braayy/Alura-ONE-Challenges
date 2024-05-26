package io.github.braayy.literalura.LiterAlura.model;

import io.github.braayy.literalura.LiterAlura.dto.BookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Setter
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;

    private String language;

    private Integer downloads;

    public Book(BookDTO dto) {
        this.title = dto.title();
        this.language = dto.languages().get(0);
        this.downloads = dto.downloads();
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", author=" + author.getName() +
            ", language='" + language + '\'' +
            ", downloads=" + downloads +
            '}';
    }
}
