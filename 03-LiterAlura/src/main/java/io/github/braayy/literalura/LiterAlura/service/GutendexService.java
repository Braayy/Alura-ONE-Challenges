package io.github.braayy.literalura.LiterAlura.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.braayy.literalura.LiterAlura.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public GutendexService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public Optional<BookDTO> searchBook(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://gutendex.com/books/?search=" + encodedTitle))
            .build();

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            APIResponse apiResponse = this.mapper.readValue(response.body(), APIResponse.class);

            return apiResponse.results().stream().findFirst();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record APIResponse(
        List<BookDTO> results
    ) {}

}
