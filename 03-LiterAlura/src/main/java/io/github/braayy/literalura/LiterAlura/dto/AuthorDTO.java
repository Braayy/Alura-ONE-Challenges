package io.github.braayy.literalura.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(
    String name,
    @JsonAlias("birth_year") int birthYear,
    @JsonAlias("death_year") int deathYear
) {}
