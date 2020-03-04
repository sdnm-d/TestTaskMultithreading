package ru.dashinimaev.task;

import org.apache.log4j.Logger;
import ru.dashinimaev.task.exceptions.AccountHasNotEnoughMoneyException;
import ru.dashinimaev.task.model.Account;
import ru.dashinimaev.task.model.Transaction;
import ru.dashinimaev.task.service.TransactionService;

import java.util.List;
import java.util.Random;

public class TransactionsBot implements Runnable {

    private static final Logger log = Logger.getLogger(TransactionsBot.class);

    private TransactionService transactionService;

    private List<Account> accounts;

    private int transactionsCount;

    private Random random = new Random();

    public TransactionsBot(TransactionService transactionService, List<Account> accounts, int transactionsCount) {
        this.transactionService = transactionService;
        this.accounts = accounts;
        this.transactionsCount = transactionsCount;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(1001) + 1000);

            for (int i = 0; i < transactionsCount; i++) {
                prepareAndMakeTransaction();
            }
        } catch (AccountHasNotEnoughMoneyException e) {
            log.info(e, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Creates random parameters for transaction
     * and makes transaction
     *
     * @throws AccountHasNotEnoughMoneyException
     */
    private void prepareAndMakeTransaction() throws AccountHasNotEnoughMoneyException {
        int indexFrom = random.nextInt(accounts.size());
        int indexTo;

        do {
            indexTo = random.nextInt(accounts.size());
        } while (indexTo == indexFrom);

        Account accountFrom = accounts.get(indexFrom);
        Account accountTo = accounts.get(indexTo);
        int sum = random.nextInt(accountFrom.getMoney() + 1);

        Transaction transaction = new Transaction(accountFrom, accountTo, sum);
        transactionService.makeTransaction(transaction);
    }
}
