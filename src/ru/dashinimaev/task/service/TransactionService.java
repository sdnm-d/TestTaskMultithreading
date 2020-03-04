package ru.dashinimaev.task.service;

import ru.dashinimaev.task.exceptions.AccountHasNotEnoughMoneyException;
import org.apache.log4j.Logger;
import ru.dashinimaev.task.model.Account;
import ru.dashinimaev.task.model.Transaction;

public class TransactionService {

    private static final Logger log = Logger.getLogger(TransactionService.class);

    /**
     * Defines order of synchronization by comparing accounts ids
     * to avoid 'Deadlock' and makes transaction on synchronized objects
     *
     * @param transaction
     * @throws AccountHasNotEnoughMoneyException
     */
    public void makeTransaction(Transaction transaction) throws AccountHasNotEnoughMoneyException {
        Account accountFrom = transaction.getAccountFrom();
        Account accountTo = transaction.getAccountTo();

        String fromId = accountFrom.getId();
        String toId = accountTo.getId();

        if(fromId.compareTo(toId) < 0) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    doTransaction(transaction);
                }
            }
        } else {
            synchronized (accountTo) {
                synchronized (accountFrom) {
                    doTransaction(transaction);
                }
            }
        }

    }

    /**
     * Do a debit/credit operations on accounts
     * and logs transaction
     *
     * @param transaction
     * @throws AccountHasNotEnoughMoneyException
     */
    private void doTransaction(Transaction transaction) throws AccountHasNotEnoughMoneyException {

        Account accountFrom = transaction.getAccountFrom();
        Account accountTo = transaction.getAccountTo();
        int sum = transaction.getSum();

        if (!checkAccountHasEnoughMoney(accountFrom.getMoney(), sum)) {
            throw new AccountHasNotEnoughMoneyException("Account " + accountFrom.getId() + " has not enough money for transaction");
        } else {
            accountFrom.subtractMoney(sum);
            accountTo.addMoney(sum);

            log.trace(transaction.toString());
        }
    }

    private boolean checkAccountHasEnoughMoney(int accountFromMoney, int sum) {
        return accountFromMoney >= sum;
    }
}
