package org.poo.cb;

import org.jetbrains.annotations.NotNull;

// Decorator Pattern
public interface Account {
    void transferMoney(double money, @NotNull Account account);
    void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions;

    void addMoney(double money);

    double getMoney();

    String getAccountType();
}

class BasicAccount implements Account {
    protected double money;
    protected String accountType;

    public BasicAccount() {
        this.money = 0;
    }

    public double getMoney() {
        return money;
    }

    public String getAccountType() {
        return accountType;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void transferMoney(double money, @NotNull Account account) {
        this.money -= money;
        account.addMoney(money);
    }

    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions {}

}

class USDAccount extends BasicAccount {
    public USDAccount() {
        this.money = 0;
        this.accountType = "USD";
    }
    @Override
    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium)  throws Exceptions {
        double wishedAmount = money * exchangeRate;
        if ( wishedAmount > this.money)
            throw new Exceptions.InsufficientFundsForExchange("Insufficient amount in account " + this.accountType +" for exchange\n");
        if ( wishedAmount > this.money * 0.5 && !statusPremium) {
            this.money -= (wishedAmount) * 0.01;
        }
        this.money -= money * exchangeRate;
        account.addMoney(money);
    }
}

class EURAccount extends BasicAccount {
    public EURAccount() {
        this.money = 0;
        this.accountType = "EUR";
    }

    @Override
    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions {
        double wishedAmount = money * exchangeRate;
        if ( wishedAmount > this.money)
            throw new Exceptions.InsufficientFundsForExchange("Insufficient amount in account " + this.accountType +" for exchange\n");
        if ( wishedAmount > this.money * 0.5 && !statusPremium) {
            this.money -= (wishedAmount) * 0.01;
        }
        this.money -= money * exchangeRate;
        account.addMoney(money);
    }
}

class GBPAccount extends BasicAccount {
    public GBPAccount() {
        this.money = 0;
        this.accountType = "GBP";
    }

    @Override
    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions {
        double wishedAmount = money * exchangeRate;
        if ( wishedAmount > this.money)
            throw new Exceptions.InsufficientFundsForExchange("Insufficient amount in account " + this.accountType +" for exchange\n");
        if ( wishedAmount > this.money * 0.5 && !statusPremium) {
            this.money -= (wishedAmount) * 0.01;
        }
        this.money -= money * exchangeRate;
        account.addMoney(money);
    }
}

class JPYAccount extends BasicAccount {
    public JPYAccount() {
        this.money = 0;
        this.accountType = "JPY";
    }

    @Override
    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions {
        double wishedAmount = money * exchangeRate;
        if ( wishedAmount > this.money)
            throw new Exceptions.InsufficientFundsForExchange("Insufficient amount in account " + this.accountType +" for exchange\n");
        if ( wishedAmount > this.money * 0.5 && !statusPremium) {
            this.money -= (wishedAmount) * 0.01;
        }
        this.money -= money * exchangeRate;
        account.addMoney(money);
    }
}

class CADAccount extends BasicAccount {
    public CADAccount() {
        this.money = 0;
        this.accountType = "CAD";
    }

    @Override
    public void exchangeMoney(double money, @NotNull Account account, String currency, double exchangeRate, boolean statusPremium) throws Exceptions {
        double wishedAmount = money * exchangeRate;
        if ( wishedAmount > this.money)
            throw new Exceptions.InsufficientFundsForExchange("Insufficient amount in account " + this.accountType +" for exchange\n");
        if ( wishedAmount > this.money * 0.5 && !statusPremium) {
            this.money -= (wishedAmount) * 0.01;
        }
        this.money -= money * exchangeRate;
        account.addMoney(money);
    }
}