package ui;

import model.OperationType;
import model.User;
import model.Wallet;
import services.AuthService;
import services.UserDataService;
import services.WalletService;
import validators.UserValidator;

import java.util.List;

public class MenuNavigator {
    public List<String> authMenu;
    public List<String> mainMenu;
    public List<String> periodMenu;
    ConsoleInput input;
    AuthService auth;
    User user;
    Wallet wallet;

    public MenuNavigator(AuthService auth) {
        this.auth = auth;
        this.input = new ConsoleInput();
        this.user = new User();
        this.wallet = new Wallet();

        List<String> authMenuList = List.of(
                "1: Создание нового аккаунта",
                "2: Авторизация",
                "3: Завершить"
        );

        List<String> mainMenuList = List.of(
                "1: Посмотреть все категории",
                "2: Посмотреть остаток",
                "3: Посмотреть общую сумму доходов",
                "4: Посмотреть общую сумму расходов",
                "5: Добавить категорию",
                "6: Удалить категорию",
                "7: Посмотреть информацию по категории",
                "8: Установить лимит на категорию",
                "9: Добавить сумму",
                "10: Снять сумму",
                "11: Посмотреть все расходы за период",
                "12: Посмотреть все доходы за период",
                "13: Выход"
        );

        List<String> periodMenu = List.of(
                "1: Последние сутки",
                "2: Три дня",
                "3: Неделя",
                "4: Месяц",
                "5: Три месяца",
                "6: Год"
        );

        this.authMenu = authMenuList;
        this.mainMenu = mainMenuList;
        this.periodMenu = periodMenu;

    }

    public void startAuthMenu() {
        input.showMessage(authMenu, "Выберите действие:");

        String value = input.readString();

        if (value.equals("3")) {
            input.endInput("Работа завершена. До скорых встреч!");
            return;
        }

        UserValidator validator = new UserValidator();

        boolean isAuth = false;
        boolean isFinish = false;
        String login = "";

        while (!isAuth && !isFinish) {
            if (login.isEmpty()) {
                input.showMessage("Введите логин");
                login = input.readString();
            }

            if (!validator.validateLogin(login)) {
                login = "";
                continue;
            }
            input.showMessage("Введите пароль");
            String password = input.readString();

            if (!validator.validatePassword(password)) {
                continue;
            }

            switch (value) {
                case "1":
                    isAuth = auth.registration(login, password, user, wallet);
                    if (isAuth) {
                        user.setLogin(login);
                    } else {
                        login = "";
                    }
                    break;
                case "2":
                    isAuth = auth.authorization(login, password, user, wallet);
                    break;
            }
        }
    }

    public void startMainMenu() {
        WalletService walletService = new WalletService();
        boolean isFinish = false;

        while (!isFinish) {
            input.showMessage(mainMenu, "Выберите действие:");
            String value = input.readString();
            String categoryIndex;
            String categoryName;

            switch (value) {
                case "1":
                    walletService.showCategories(wallet);
                    break;
                case "2":
                    System.out.println("Остаток: " + wallet.getBalance());
                    break;
                case "3":
                    System.out.println("Общая сумма расходов: " + wallet.getTotalExpenses());
                    break;
                case "4":
                    System.out.println("Общая сумма доходов: " + wallet.getTotalIncome());
                    break;
                case "5":
                    input.showMessage("Введите название категории");
                    categoryName = input.readString();
                    boolean hasCreated = walletService.createCategory(categoryName, wallet);

                    if (hasCreated) {
                        System.out.println("Категория " + categoryName + " успешно создана");
                    }
                    break;
                case "6":
                    walletService.showCategories(wallet);
                    System.out.println();
                    input.showMessage("Выберите номер категории (например 1, 2...)");
                    categoryIndex = input.readString();
                    boolean hasDeleted = walletService.deleteCategory(categoryIndex, wallet);

                    if (hasDeleted) {
                        System.out.println("Категория удалена");
                    }
                    break;
                case "7":
                    walletService.showCategories(wallet);
                    System.out.println();
                    input.showMessage("Выберите номер категории (например 1, 2...)");
                    categoryIndex = input.readString();
                    walletService.showCategory(wallet, categoryIndex);
                    break;
                case "8":
                    walletService.showCategories(wallet);
                    System.out.println();
                    input.showMessage("Выберите номер категории (например 1, 2...)");
                    categoryIndex = input.readString();
                    input.showMessage("Введите сумму");
                    double limit = input.readDouble();
                    input.readString();
                    boolean isChanged = walletService.changeLimit(wallet, categoryIndex, limit);

                    if (isChanged) {
                        System.out.println("Лимит для категории успешно установлен");
                    }
                    break;
                case "9":
                    walletService.showCategories(wallet);
                    System.out.println();
                    input.showMessage("Выберите номер категории (например 1, 2...)");
                    categoryIndex = input.readString();
                    input.showMessage("Введите сумму");
                    double income = input.readDouble();
                    input.readString();

                    boolean isIncomeAdded = walletService.addIncome(wallet, categoryIndex, income);

                    if (isIncomeAdded) {
                        System.out.println("Сумма успешно добавлена в кошелек");
                    }
                    break;
                case "10":
                    walletService.showCategories(wallet);
                    System.out.println();
                    input.showMessage("Выберите номер категории (например 1, 2...)");
                    categoryIndex = input.readString();
                    input.showMessage("Введите сумму");
                    double expense = input.readDouble();
                    input.readString();

                    boolean isExpenseAdded = walletService.addExpenses(wallet, categoryIndex, expense);

                    if (isExpenseAdded) {
                        System.out.println("Сумма успешно потрачена");
                    }
                    break;
                case "11":
                    input.showMessage(periodMenu, "Выберите период для подсчета расходов:");
                    String expensePeriod = input.readString();
                    walletService.showTotalForPeriod(OperationType.EXPENSE, expensePeriod, wallet);
                    break;
                case "12":
                    input.showMessage(periodMenu, "Выберите период для подсчета доходов:");
                    String periodVariant = input.readString();
                    walletService.showTotalForPeriod(OperationType.INCOME, periodVariant, wallet);
                    break;
                case "13":
                    UserDataService.saveData(user.getLogin(), wallet);
                    input.endInput("Завершение работы программы. До скорых встреч!");
                    isFinish = true;
                    break;
            }
        }
    }

}
