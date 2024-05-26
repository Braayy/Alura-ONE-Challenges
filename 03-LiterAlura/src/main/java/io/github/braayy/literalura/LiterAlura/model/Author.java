package io.github.braayy.literalura.LiterAlura.model;

import io.github.braayy.literalura.LiterAlura.dto.AuthorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author(AuthorDTO dto) {
        this.name = dto.name();
        this.birthYear = dto.birthYear();
        this.deathYear = dto.deathYear();
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", birthYear=" + birthYear +
            ", deathYear=" + deathYear +
            ", books=" + books.stream().map(Book::getTitle).collect(Collectors.joining(" ")) +
            '}';
    }
}
