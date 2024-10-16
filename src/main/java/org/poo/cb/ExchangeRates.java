package org.poo.cb;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRates {

    private enum Currency {
        EUR, GBP, JPY, CAD, USD
    }

    private final Map<String, Double[]> exchangeRates = new HashMap<>();

    public ExchangeRates() {}

    public Map<String, Double[]> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRateFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName));
            String line;

            br.readLine();
            // Skip first line
            line = br.readLine();
            while (line != null) {
                String[] vars = line.split(",");
                Double[] exchangeRatesValues = new Double[5];
                for (int i = 1; i < vars.length; i++) {
                    exchangeRatesValues[i - 1] = Double.parseDouble(vars[i]);
                }
                exchangeRates.put(vars[0], exchangeRatesValues);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getExchangeRate(String currency, String currencyTo) {
        Double[] exchangeRatesValues = exchangeRates.get(currency);

        if (exchangeRatesValues == null) {
            throw new RuntimeException("Currency not supported");
        }

        return switch (currencyTo) {
            case "EUR" -> exchangeRatesValues[0];
            case "GBP" -> exchangeRatesValues[1];
            case "JPY" -> exchangeRatesValues[2];
            case "CAD" -> exchangeRatesValues[3];
            case "USD" -> exchangeRatesValues[4];
            default -> throw new RuntimeException("Currency not supported");
        };
    }
}
