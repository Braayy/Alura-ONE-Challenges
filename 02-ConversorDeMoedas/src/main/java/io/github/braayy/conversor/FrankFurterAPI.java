package io.github.braayy.conversor;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FrankFurterAPI {

    public RateResponse getRates() throws IOException, InterruptedException {
        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.frankfurter.app/latest?from=BRL")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Não foi possível acessar a API de conversão");
        }

        String body = response.body();

        RateResponse rateResponse = gson.fromJson(body, RateResponse.class);

        rateResponse.rates.put("BRL", 1.0);

        return rateResponse;
    }

    public record RateResponse(
        double amount,
        String base,
        String date,
        Map<String, Double> rates
    ) {}

}
