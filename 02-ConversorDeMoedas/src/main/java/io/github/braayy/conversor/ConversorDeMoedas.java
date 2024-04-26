package io.github.braayy.conversor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ConversorDeMoedas {

    public static void main(String[] args) {
        FrankFurterAPI api = new FrankFurterAPI();
        CurrencyConverter converter = new CurrencyConverter();
        ConversionLogger logger = new ConversionLogger("historico.txt");

        FrankFurterAPI.RateResponse rateResponse;

        try {
            rateResponse = api.getRates();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao pegar as taxas de conversão das moedas: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("*".repeat(28));
        System.out.println("*" + " ".repeat(26) + "*");
        System.out.println("* Conversor de Moedas v1.0 *");
        System.out.println("*" + " ".repeat(26) + "*");
        System.out.println("*".repeat(28));

        while (true) {
            System.out.print("\nDigite o valor que deseja converter(ou digite sair): ");

            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("sair")) {
                break;
            }

            double amount = Double.parseDouble(response);

            printCurrencies(rateResponse.rates());

            String from;
            do {
                System.out.print("\nDigite a moeda do valor que você digitou: ");
                from = scanner.nextLine().toUpperCase(Locale.ROOT);
            } while (!rateResponse.rates().containsKey(from));

            printCurrencies(rateResponse.rates());

            String to;
            do {
                System.out.print("\nDigite a moeda para qual deseja converter: ");
                to = scanner.nextLine().toUpperCase(Locale.ROOT);
            } while (!rateResponse.rates().containsKey(to));

            CurrencyConverter.ConversionResult result = converter.convertCurrency(rateResponse.rates(), from, to, amount);

            System.out.printf(
                "\nBaseando-se na taxa de %.3f %s por %s:\n  %.3f %s = %.3f %s\n",
                result.rate(), from, to,
                amount, from,
                result.convertedAmount(), to
            );

            try {
                logger.log(from, to, amount, result.convertedAmount());
            } catch (IOException exception) {
                System.err.println("Não foi possível armazenar o histórico de conversões: " + exception.getMessage());
            }
        }
    }

    private static void printCurrencies(Map<String, Double> rates) {
        Iterator<String> iterator = rates.keySet().iterator();
        while (iterator.hasNext()) {
            for (int i = 0; i < 6; i++) {
                if (iterator.hasNext()) {
                    System.out.print(iterator.next() + " ");
                }
            }

            System.out.println();
        }
    }
}
