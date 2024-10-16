package org.poo.cb;

import org.jetbrains.annotations.NotNull;

import java.util.*;


public class Portofolio {
    private final HashMap<String, Account> accounts = new HashMap<>();
    private final TreeMap<String, Double> stocks = new TreeMap<>();

    public Portofolio() {
    }

    public void addAccount(String currency) throws Exceptions {
        if (accounts.containsKey(currency)) {
            throw new Exceptions.AccountAlreadyExists("Account with " + currency + " already exists\n");
        }
        switch (currency) {
            case "USD":
                accounts.put(currency, new USDAccount());
                break;
            case "EUR":
                accounts.put(currency, new EURAccount());
                break;
            case "GBP":
                accounts.put(currency, new GBPAccount());
                break;
            case "JPY":
                accounts.put(currency, new JPYAccount());
                break;
            case "CAD":
                accounts.put(currency, new CADAccount());
                break;
            default:
                throw new RuntimeException("Currency not found\n");
        }
    }

    public Account getAccount(String currency) {
        return accounts.get(currency);
    }

    public void addMoney(double money, String currency) {
        accounts.get(currency).addMoney(money);
    }

    public void exchangeMoney(double money, String currency, String currencyTo, @NotNull ExchangeRates exchangeRates, boolean statusPremium) throws Exceptions {
        Account giveAccount = getAccount(currency);
        Account revieveAccount = getAccount(currencyTo);
        double exchangeRate = exchangeRates.getExchangeRate(currencyTo, currency);

        if (giveAccount == null) {
            throw new RuntimeException("Couldn't find the currency account");
        }
        if (revieveAccount == null) {
            throw new RuntimeException("Couldn't find the currencyTo account");
        }

        giveAccount.exchangeMoney(money, revieveAccount, currencyTo, exchangeRate, statusPremium);
    }

    public String listPortfolio() {
        // Adding the stocks to the output
        boolean hasStocks = false;
        String output = "{\"stocks\":[";

        for (Map.Entry<String, Double> entry : stocks.entrySet()) {
            output += "{\"stockName\":\"" + entry.getKey() + "\",\"amount\":" + entry.getValue().intValue() + "},";
            hasStocks = true;
        }
        if (hasStocks)
            output = output.substring(0, output.length() - 1);
        output += "]";

        // Adding the accounts to the output
        output += ",\"accounts\":[";
        String[] keys = {"USD", "EUR", "GBP", "JPY", "CAD"};
        for (String key : keys) {
            if (accounts.containsKey(key)) {
                output += "{\"currencyName\":\"" + key + "\",\"amount\":\"" + String.format("%.2f", accounts.get(key).getMoney()) + "\"},";
            }
        }
        output = output.substring(0, output.length() - 1);
        output += "]}\n";
        return output;
    }

    public void transferMoney(double money, @NotNull Account userAccount, Account friendAccount, String friendEmail) throws Exceptions {
        if (userAccount.getMoney() < money)
            throw new Exceptions.InsufficientFundsForTransfer("Insufficient amount in account " + userAccount.getAccountType() + " for transfer\n");
        if (friendAccount == null)
            throw new Exceptions.InvalidTransfer("You are not allowed to transfer money to " + friendEmail);
        userAccount.addMoney(money * -1.0);
        friendAccount.addMoney(money);
    }

    public void addStock(String stockName, Double quantity) {
        if (stocks.containsKey(stockName)) {
            stocks.put(stockName, stocks.get(stockName) + quantity);
        } else {
            stocks.put(stockName, (double) quantity);
        }
    }
}