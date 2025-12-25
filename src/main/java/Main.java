import services.AuthService;
import ui.MenuNavigator;

public class Main {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        MenuNavigator navigator = new MenuNavigator(auth);

        navigator.startAuthMenu();

        if (auth.getIsAuthorized()) {
            navigator.startMainMenu();
        }
    }
}