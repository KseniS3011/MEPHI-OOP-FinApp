import model.User;
import model.Wallet;
import ui.ConsoleInput;
import services.AuthService;
import ui.MenuNavigator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        MenuNavigator navigator = new MenuNavigator(auth);

        navigator.startAuthMenu();

        if(auth.getIsAuthorized()) {
            navigator.startMainMenu();
        }

    }
}