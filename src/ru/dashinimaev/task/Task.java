package ru.dashinimaev.task;

import ru.dashinimaev.task.model.Account;
import ru.dashinimaev.task.service.BotService;
import ru.dashinimaev.task.service.TransactionService;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private static final int moneyAmount = 10000;
    private static final int botsNumber = 10;
    private static final int maxTransactions = 30;

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            UID uid = new UID();
            Account account = new Account(uid.toString(), moneyAmount);
            accounts.add(account);
        }

        TransactionService transactionService = new TransactionService();
        BotService botService = new BotService(transactionService);

        botService.emulate(accounts, botsNumber, maxTransactions);
    }
}
