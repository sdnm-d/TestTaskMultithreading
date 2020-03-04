package ru.dashinimaev.task.exceptions;

public class AccountHasNotEnoughMoneyException extends Exception {

    public AccountHasNotEnoughMoneyException(String message) {
        super(message);
    }
}
