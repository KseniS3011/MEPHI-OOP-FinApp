package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Wallet {
    private double balance;
    private double totalExpenses;
    private double totalIncome;
    private Map<String, CategoryInfo> categories;

    public static final String WITHOUT_CATEGORY_NAME = "Без категории";

    public Wallet() {
        this.balance = 0;
        this.totalExpenses = 0;
        this.totalIncome = 0;
        this.categories = new LinkedHashMap<>();
        this.categories.put("1", new CategoryInfo(WITHOUT_CATEGORY_NAME));
    }

    public static class CategoryInfo {
        private String name;
        private double limit;
        private double expenses;
        private double income;
        private History history;


        public CategoryInfo() {
            this.history = new History();
        }

        public CategoryInfo(String name) {
            this.name = name;
            this.limit = 0;
            this.expenses = 0;
            this.income = 0;
            this.history = new History();
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }

        public double getExpenses() {
            return expenses;
        }

        public void setExpenses(double expenses) {
            this.expenses = expenses;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public History getHistory() {
            return this.history;
        }

        public void setHistory(History history) {
            this.history = history;
        }

        public void addHistory(double amount, OperationType type) {
            this.history.add(amount, type);
        }

        public void showCategoryInfo() {
            System.out.println();
            System.out.println("Название категории: " + getName());
            System.out.println("Лимит: " + getLimit());
            System.out.println("Доход: " + getIncome());
            System.out.println("Расход: " + getExpenses());
            history.print();
            System.out.println();
        }
    }

    public Map<String, CategoryInfo> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, CategoryInfo> categories) {
        this.categories = categories;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalExpenses() { return totalExpenses; }

    public void setTotalExpenses(double totalExpenses) { this.totalExpenses = totalExpenses; }

    public double getTotalIncome() { return totalIncome; }

    public void setTotalIncome(double totalIncome) { this.totalIncome = totalIncome; }
}
