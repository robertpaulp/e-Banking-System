package org.poo.cb;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommandEnum {
    String getCommand();

    @Nullable
    static <T extends CommandEnum> T findCommand(@NotNull Class<T> enumClass, String command) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCommand().equals(command)) {
                return enumConstant;
            }
        }
        return null;
    }
}
enum UserCommandsTypes implements CommandEnum {
    CREATE ("CREATE USER"),
    ADD_FRIEND ("ADD FRIEND"),
    LIST_USER ("LIST USER"),
    BUY_PREMIUM ("BUY PREMIUM");

    private final String command;

    UserCommandsTypes(String command) {
        this.command = command;
    }

    @Override
    public String getCommand() {
        return this.command;
    }

}

enum AccountCommandsOptions implements CommandEnum {
    ADD_ACCOUNT ("ADD ACCOUNT"),
    ADD_MONEY ("ADD MONEY"),
    EXCHANGE_MONEY ("EXCHANGE MONEY"),
    TRANSFER_MONEY ("TRANSFER MONEY"),
    LIST_PORTFOLIO ("LIST PORTFOLIO");

    private final String command;

    AccountCommandsOptions(String command) {
        this.command = command;
    }

    @Override
    public String getCommand() {
        return this.command;
    }
}

enum StockCommandsOptions implements CommandEnum {
    BUY_STOCKS ("BUY STOCKS"),
    RECOMMEND_STOCKS ("RECOMMEND STOCKS");

    private final String command;

    StockCommandsOptions(String command) {
        this.command = command;
    }

    @Override
    public String getCommand() {
        return this.command;
    }
}
