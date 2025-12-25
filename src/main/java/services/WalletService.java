package services;

import model.Operation;
import model.OperationType;
import model.Wallet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WalletService {

    public boolean isCategoryAbsent(Map<String, Wallet.CategoryInfo> categories, String name) {
        if (categories.get(name) == null) {
            System.out.println("Категория отсутствует");
            return true;
        }
        return false;
    }

    public boolean createCategory(String name, Wallet wallet) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();
        for (String categoryIndex : categories.keySet()) {
            if (categories.get(categoryIndex).getName().equals(name)) {
                System.out.println("Категория уже существует");
                return false;
            }
        }

        int nextIndex = wallet.getCategories().size() + 1;

        wallet.getCategories().put(String.valueOf(nextIndex), new Wallet.CategoryInfo(name));
        return true;
    }

    private void reindexCategories(Wallet wallet) {
        Map<String, Wallet.CategoryInfo> oldCategories = wallet.getCategories();
        Map<String, Wallet.CategoryInfo> newCategories = new LinkedHashMap<>();

        int index = 1;
        for (Wallet.CategoryInfo category : oldCategories.values()) {
            newCategories.put(String.valueOf(index), category);
            index++;
        }

        wallet.setCategories(newCategories);
    }

    public boolean deleteCategory(String categoryIndex, Wallet wallet) {
        if (categoryIndex.equals("1")) {
            System.out.println("Данную категорию невозможно удалить");
            return false;
        }

        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();

        if (isCategoryAbsent(categories, categoryIndex)) {
            return false;
        }

        Wallet.CategoryInfo common = wallet.getCategories().get("1");

        Wallet.CategoryInfo category = categories.get(categoryIndex);
        common.setExpenses(common.getExpenses() + category.getExpenses());
        common.setIncome(common.getIncome() + category.getIncome());

        ArrayList<Operation> history = category.getHistory().getHistory();
        ArrayList<Operation> commonHistory = common.getHistory().getHistory();
        commonHistory.addAll(history);

        wallet.getCategories().remove(categoryIndex);
        reindexCategories(wallet);
        return true;
    }

    public boolean addIncome(Wallet wallet, String categoryIndex, double value) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();

        if (isCategoryAbsent(categories, categoryIndex)) {
            return false;
        }

        String currentIndex = categoryIndex.isEmpty() ? "1" : categoryIndex;
        Wallet.CategoryInfo category = categories.get(currentIndex);

        category.setIncome(category.getIncome() + value);
        wallet.setBalance(wallet.getBalance() + value);
        wallet.setTotalIncome(wallet.getTotalIncome() + value);
        category.addHistory(value, OperationType.INCOME);

        return true;
    }

    public boolean addExpenses(Wallet wallet, String categoryIndex, double value) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();

        if (isCategoryAbsent(categories, categoryIndex)) {
            return false;
        }

        String currentIndex = categoryIndex.isEmpty() ? "1" : categoryIndex;
        Wallet.CategoryInfo category = categories.get(currentIndex);

        double limitValue = category.getLimit();
        double expenses = category.getExpenses();
        double balance = wallet.getBalance();
        double newExpenses = expenses + value;

        if (value > balance) {
            System.out.println("Недостаточно средств");
            return false;
        }

        if (newExpenses <= limitValue) {
            category.setExpenses(expenses + value);
            wallet.setTotalExpenses(wallet.getTotalExpenses() + value);
            wallet.setBalance(balance - value);
            category.addHistory(value, OperationType.EXPENSE);

            if (newExpenses == limitValue) {
                System.out.println(
                        "Лимит исчерпан. " +
                                "Для того, чтобы совершать покупки в данной категории, необходимо изменить лимит."
                );
            } else if (newExpenses >= limitValue * 0.8) {
                System.out.println("Израсходовано более 80% лимита.");
            }

            System.out.println("Остаток для категории " + category.getName() + ": " + (limitValue - newExpenses));

            return true;
        } else {
            System.out.println("Превышение лимита. Введите другую сумму");
        }
        return false;
    }

    public boolean changeLimit(Wallet wallet, String categoryIndex, double value) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();

        if (isCategoryAbsent(categories, categoryIndex)) {
            return false;
        }

        Wallet.CategoryInfo category = categories.get(categoryIndex);
        category.setLimit(value);
        return true;
    }

    public void showCategories(Wallet wallet) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();
        System.out.println();
        System.out.println("Все категории:");
        System.out.println();

        for (String key : categories.keySet()) {
            System.out.println(key + " - " + categories.get(key).getName());
        }
    }

    public void showCategory(Wallet wallet, String categoryIndex) {
        Map<String, Wallet.CategoryInfo> categories = wallet.getCategories();

        if (isCategoryAbsent(categories, categoryIndex)) {
            return;
        }
        Wallet.CategoryInfo category = categories.get(categoryIndex);
        category.showCategoryInfo();
    }

    public LocalDateTime getStartOfPeriod(String period) {
        LocalDateTime now = LocalDateTime.now();

        return switch (period) {
            case "1" -> now.minusDays(1);
            case "2" -> now.minusDays(3);
            case "3" -> now.minusWeeks(1);
            case "4" -> now.minusMonths(1);
            case "5" -> now.minusMonths(3);
            case "6" -> now.minusYears(1);
            default -> null;
        };
    }

    public double getTotalForPeriod(String period, OperationType type, Wallet wallet) {
        LocalDateTime start = getStartOfPeriod(period);
        LocalDateTime now = LocalDateTime.now();
        double total = 0;

        for (Wallet.CategoryInfo category : wallet.getCategories().values()) {
            ArrayList<Operation> history = category.getHistory().getHistory();

            for (Operation operation : history) {
                LocalDateTime date = operation.getDateTime();

                if (operation.getType() == type && (date.isAfter(start) || date.isEqual(start)) &&
                        date.isBefore(now)) {
                    total += operation.getAmount();
                }
            }
        }
        return total;
    }

    public void showTotalForPeriod(OperationType type, String periodVariant, Wallet wallet) {
        LocalDateTime startOfPeriod = getStartOfPeriod(periodVariant);

        if (startOfPeriod == null) {
            System.out.println("Некорректный выбор периода");
        } else {
            double total = getTotalForPeriod(periodVariant, type, wallet);
            String text = type == OperationType.EXPENSE ? "Расходы" : "Доходы";
            System.out.println(text + " за период: " + total);
        }
    }
}
