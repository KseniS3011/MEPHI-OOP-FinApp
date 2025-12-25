package validators;

public class UserValidator {
    public boolean validateLogin(String login) {
        boolean isValidated = false;
        if (login.length() < 3) {
            System.out.println("Логин должен состоять минимум из 3 символов");
        } else if (!login.matches("[A-Za-z0-9]+")) {
            System.out.println("Логин должен состоять только из латинских символов или цифр");
        } else {
            isValidated = true;
        }

        return isValidated;
    }

    public boolean validatePassword(String password) {
        boolean isValidated = false;
        if (password.length() < 6) {
            System.out.println("Пароль должен состоять минимум из 6 символов");
        } else {
            isValidated = true;
        }
        return isValidated;
    }
}
