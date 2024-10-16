package org.poo.cb;

public class Exceptions extends Exception{
    public Exceptions(String message) {
        super(message);
        System.out.println(message);
    }

    static class UserAlreadyExists extends Exceptions {
        public UserAlreadyExists(String message) {
            super(message);
        }
    }

    static class UserDoesntExist extends Exceptions {
        public UserDoesntExist(String message) {
            super(message);
        }
    }

    static class UserIsAlreadyFriend extends Exceptions {
        public UserIsAlreadyFriend(String message) {
            super(message);
        }
    }

    static class AccountAlreadyExists extends Exceptions {
        public AccountAlreadyExists(String message) {
            super(message);
        }
    }

    static class InsufficientFundsForExchange extends Exceptions {
        public InsufficientFundsForExchange(String message) {
            super(message);
        }
    }

    static class InsufficientFundsForTransfer extends Exceptions {
        public InsufficientFundsForTransfer(String message) {
            super(message);
        }
    }

    static class InvalidTransfer extends Exceptions {
        public InvalidTransfer(String message) {
            super(message);
        }
    }

    static class InsufficientFundsForStocks extends Exceptions {
        public InsufficientFundsForStocks(String message) {
            super(message);
        }
    }
}
