package ru.dashinimaev.task.model;

public class Transaction {
    private Account accountFrom;
    private Account accountTo;
    private int sum;

    public Transaction(Account accountFrom, Account accountTo, int sum) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.sum = sum;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Transaction from " + accountFrom.getId() + " to " + accountTo.getId() + ". Sum is " + sum + "." +
                "\nResults: accountFrom money = " + accountFrom.getMoney() + ", accountTo money = " + accountTo.getMoney() +".";
    }
}
