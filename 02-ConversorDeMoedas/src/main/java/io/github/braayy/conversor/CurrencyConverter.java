package io.github.braayy.conversor;

import java.util.Map;

public class CurrencyConverter {

    public ConversionResult convertCurrency(Map<String, Double> rates, String from, String to, double amount) {
        if (from.equals(to)) {
            return new ConversionResult(1, amount);
        }

        double fromRate = rates.get(from);
        double toRate = rates.get(to);

        double rate = toRate / fromRate;
        double convertedAmount = amount * rate;

        return new ConversionResult(rate, convertedAmount);
    }

    public record ConversionResult(
        double rate,
        double convertedAmount
    ) {}

}
