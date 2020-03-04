package ru.dashinimaev.task.service;

import ru.dashinimaev.task.TransactionsBot;
import org.apache.log4j.Logger;
import ru.dashinimaev.task.model.Account;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BotService {
    private static final Logger log = Logger.getLogger(BotService.class);

    private TransactionService transactionService;

    public BotService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Creates bots and threads for transactions handling
     *
     * @param accounts list of accounts
     * @param botCount number of bots
     * @param maxTransactions number of transactions
     */
    public void emulate(List<Account> accounts, int botCount, int maxTransactions) {
        ExecutorService executorService = Executors.newFixedThreadPool(botCount);
        log.info("Method emulate started. All accounts money = " + getAllAccountsMoney(accounts));

        for (int i = 0; i < botCount; i++) {
            int oneBotTransactionsCount = maxTransactions / botCount;
            TransactionsBot bot = new TransactionsBot(transactionService, accounts, oneBotTransactionsCount);
            executorService.submit(bot);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            log.info("Finished. All accounts money = " + getAllAccountsMoney(accounts));
        }
    }

    private int getAllAccountsMoney(List<Account> accounts) {
        int accountsMoney = 0;

        for (Account acc: accounts) {
            accountsMoney += acc.getMoney();
        }

        return accountsMoney;
    }
}
