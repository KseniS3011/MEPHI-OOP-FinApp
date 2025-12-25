package model;

import java.time.LocalDateTime;

public class Operation {

    private double amount;
    private OperationType type;
    private LocalDateTime dateTime;

    public Operation() {
    }

    public Operation(double amount, OperationType type) {
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public OperationType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
