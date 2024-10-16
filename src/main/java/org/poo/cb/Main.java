package org.poo.cb;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Main {
    private static void prepareData(@NotNull ExchangeRates exchangeRates, @NotNull StockCollection stockCollection, @NotNull String[] args) {
        exchangeRates.setExchangeRateFromFile(args[0]);
        stockCollection.setStockPricesFromFile(args[1]);
    }
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");
            return;
        }
        if (args.length != 3) {
            System.out.println("Not enough arguments");
            System.out.println("The required number of arguments is 3");
            return;
        }
        ExchangeRates exchangeRates = new ExchangeRates();
        StockCollection stockCollection = new StockCollection();
        ExecuteCommand executeCommand = new ExecuteCommand();

        Users users = Users.getInstance();
        prepareData(exchangeRates, stockCollection, args);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + args[2]));
            String line = br.readLine();
            while (line != null) {
                String[] vars = line.split(" ");
                String command = vars[0] + " " + vars[1];
                executeCommand.getInstanceOfCommand(command);
                executeCommand.executeCommand(vars, exchangeRates, stockCollection);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        users.resetData();
    }
}