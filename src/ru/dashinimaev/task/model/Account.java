package ru.dashinimaev.task.model;

public class Account {

    private String id;

    private int money;

    public Account(String id, int money) {
        this.id = id;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int sum) {
        money += sum;
    }

    public void subtractMoney(int sum) {
        money -= sum;
    }
}
