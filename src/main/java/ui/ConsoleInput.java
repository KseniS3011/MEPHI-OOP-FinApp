package ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showMessage(List<String> messages, String firstMessage) {
        System.out.println();
        System.out.println(firstMessage);
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println();
    }

    public double readDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод! Введите число");
                scanner.nextLine();
            }
        }
    }

    public String readString() {
        while (true) {
            try {
                return scanner.nextLine().trim();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод! Введите строку");
                scanner.nextLine();
            }
        }
    }

    public void endInput(String message) {
        System.out.println(message);
        scanner.close();
    }
}
