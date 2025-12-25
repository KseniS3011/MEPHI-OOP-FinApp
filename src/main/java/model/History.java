package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class History {
    private ArrayList<Operation> history;

    History() {
        this.history = new ArrayList<>();
    }

    public void add(double amount, OperationType type) {
        Operation operation = new Operation(amount, type);
        history.add(operation);
    }

    public void print() {
        if (history.isEmpty()) {
            System.out.println("История операций отсутствует");
            return;
        }
        System.out.println("История операций:");
        for (Operation operation : history) {
            LocalDateTime dateTime = operation.getDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formatted = dateTime.format(formatter);
            String text = operation.getType().equals(OperationType.EXPENSE) ? "Расход" : "Доход";

            System.out.println();
            System.out.println("Дата: " + formatted);
            System.out.println("Тип операции: " + text);
            System.out.println("Сумма: " + operation.getAmount());
            System.out.println();
        }
    }

    public void setHistory(ArrayList<Operation> history) {
        this.history = history;
    }

    public ArrayList<Operation> getHistory() {
        return history;
    }
}
