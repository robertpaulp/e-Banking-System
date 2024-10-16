package org.poo.cb;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

// Command Design Pattern
interface Command {
    void execute(CommandEnum command, String[] args, ExchangeRates exchangeRates, StockCollection stockCollection);
}

class UserCommand implements Command {
    @Override
    public void execute(CommandEnum command, String[] args, ExchangeRates exchangeRates, StockCollection stockCollection) {
        UserCommandsTypes userCommandsTypes = (UserCommandsTypes) command;
        Users users = Users.getInstance();

        switch (userCommandsTypes) {
            case CREATE:
                CreateUserCommand.create(users, args);
                break;
            case ADD_FRIEND:
                AddFriendCommand.addFriend(users, args);
                break;
            case LIST_USER:
                ListUserCommand.listUser(users, args);
                break;
            case BUY_PREMIUM:
                CreatePremiumUserCommand.createPremium(users, args);
                break;
            default:
                System.out.println("Command not found in UserCommand");
                break;
        }

    }
}

//region UserCommandsOptions

class CreateUserCommand {
    public static void create(Users users, String[] args) {
        try {
            String address = "";
            for (int i = 5; i < args.length; i++) {
                address += args[i] + " ";
            }
            address = address.trim();
            users.addUser(new Users.User(args[2], args[3], args[4], address));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class AddFriendCommand {
    public static void addFriend(Users users, String[] args) {
        try {
            users.addFriend(args[2], args[3]);
            users.addFriend(args[3], args[2]);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ListUserCommand {
    public static void listUser(Users users, String[] args) {
        try {
            System.out.print(users.listUser(args[2]));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class CreatePremiumUserCommand {
    public static void createPremium(Users users, String[] args) {
        try {
            Users.User user = users.getUser(args[2]);
            Account account = user.getPortofolio().getAccount("USD");
            if (account.getMoney() < 100) {
                System.out.println("Insufficient amount in account for buying premium option");
                return;
            }
            user.setStatusPremium(true);
            account.addMoney(-100);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
//endregion

class AccountCommand implements Command {
    @Override
    public void execute(CommandEnum command, String[] args, ExchangeRates exchangeRates, StockCollection stockCollection) {
        AccountCommandsOptions accountCommandsTypes = (AccountCommandsOptions) command;
        Users users = Users.getInstance();

        switch (accountCommandsTypes) {
            case ADD_ACCOUNT:
                AddAccountCommand.addAccount(users, args);
                break;
            case ADD_MONEY:
                AddMoneyCommand.addMoney(users, args);
                break;
            case EXCHANGE_MONEY:
                ExchangeMoneyCommand.exchangeMoney(users, args, exchangeRates);
                break;
            case TRANSFER_MONEY:
                TransferMoneyCommand.transferMoney(users, args);
                break;
            case LIST_PORTFOLIO:
                ListPortofolioCommand.listPortofolio(users, args);
                break;
            default:
                System.out.println("Command not found in AccountCommand");
                break;
        }
    }
}

//region AccountCommandsOptions

class AddAccountCommand {
    public static void addAccount(Users users, String[] args) {
        try {
            Users.User user = users.getUser(args[2]);
            Portofolio portofolio = user.getPortofolio();
            portofolio.addAccount(args[3]);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class AddMoneyCommand {
    public static void addMoney(Users users, String[] args) {
        try {
            Users.User user = users.getUser(args[2]);
            Portofolio portofolio = user.getPortofolio();
            portofolio.addMoney(Double.parseDouble(args[4]), args[3]);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ExchangeMoneyCommand {
    public static void exchangeMoney(Users users, String[] args, ExchangeRates exchangeRates) {
        try {
            Users.User user = users.getUser(args[2]);
            Portofolio portofolio = user.getPortofolio();
            portofolio.exchangeMoney(Double.parseDouble(args[5]), args[3], args[4], exchangeRates, user.getStatusPremium());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class TransferMoneyCommand {
    public static void transferMoney(Users users, String[] args) {
        try {
            Users.User user = users.getUser(args[2]);
            Users.User friend = users.getUser(args[3]);
            Portofolio portofolio = user.getPortofolio();
            Portofolio friendPortofolio = friend.getPortofolio();
            portofolio.transferMoney(Double.parseDouble(args[5]), portofolio.getAccount(args[4]), friendPortofolio.getAccount(args[4]), args[3]);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ListPortofolioCommand {
    public static void listPortofolio(Users users, String[] args) {
        try {
            Users.User user = users.getUser(args[2]);
            Portofolio portofolio = user.getPortofolio();
            System.out.print(portofolio.listPortfolio());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
//endregion

class StockCommand implements Command {
    @Override
    public void execute(CommandEnum command, String[] args, ExchangeRates exchangeRates, @NotNull StockCollection stockCollection) {
        StockCommandsOptions stockCommandsTypes = (StockCommandsOptions) command;
        Users users = Users.getInstance();
        RecommendStock recommendStock = new RecommendStock(stockCollection.getStocks());

        switch (stockCommandsTypes) {
            case BUY_STOCKS:
                BuyStocksCommand.buyStocks(users, args, stockCollection, recommendStock);
                break;
            case RECOMMEND_STOCKS:
                RecommendStocksCommand.recommendStocks(recommendStock, stockCollection);
                break;
            default:
                System.out.println("Command not found in StockCommand");
                break;
        }
    }
}

//region StockCommandsOptions

class BuyStocksCommand {
    public static void buyStocks(Users users, String[] args, StockCollection stockCollection, RecommendStock recommendStock) {
        try {
            Users.User user = users.getUser(args[2]);
            Portofolio portofolio = user.getPortofolio();
            BuyStock buyStock = new BuyStock(args[3]);
            Double amount = Double.parseDouble(args[4]);
            buyStock.performAction(stockCollection.getStocks());
            buyStock.modifyStocks(portofolio, args[3], amount, user.getStatusPremium(), recommendStock.getRecommendedStockList());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class RecommendStocksCommand {
    public static void recommendStocks(@NotNull RecommendStock recommendStock, @NotNull StockCollection stockCollection) {
        recommendStock.performAction(stockCollection.getStocks());
    }
}
//endregion

class ExecuteCommand {
    private Command command;
    private CommandEnum commandEnum;
    private final ArrayList<String> userCommands = new ArrayList<>(Arrays.asList(
            "CREATE USER",
            "ADD FRIEND",
            "LIST USER",
            "BUY PREMIUM"
    ));
    private final ArrayList<String> accountCommands = new ArrayList<>(Arrays.asList(
            "ADD ACCOUNT",
            "ADD MONEY",
            "EXCHANGE MONEY",
            "TRANSFER MONEY",
            "LIST PORTFOLIO"
    ));
    private final ArrayList<String> stockCommands = new ArrayList<>(Arrays.asList(
            "BUY STOCKS",
            "RECOMMEND STOCKS"
    ));

    public ExecuteCommand() {
    }

    private void setCommand(Command command) {
        this.command = command;
    }

    private void setCommandEnum(CommandEnum commandEnum) {
        this.commandEnum = commandEnum;
    }

    public void executeCommand(String[] args, ExchangeRates exchangeRates, StockCollection stockCollection) {
        command.execute(commandEnum, args, exchangeRates, stockCollection);
    }

    public void getInstanceOfCommand(String command) {
        if (command == null) {
            return;
        }
        if (userCommands.contains(command)) {
            setCommand(new UserCommand());
            setCommandEnum(CommandEnum.findCommand(UserCommandsTypes.class, command));
        } else if (accountCommands.contains(command)) {
            setCommand(new AccountCommand());
            setCommandEnum(CommandEnum.findCommand(AccountCommandsOptions.class, command));
        } else if (stockCommands.contains(command)) {
            setCommand(new StockCommand());
            setCommandEnum(CommandEnum.findCommand(StockCommandsOptions.class, command));
        } else {
            System.out.println("Command not found");
        }
    }
}