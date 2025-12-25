package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Wallet;

import model.UserData;
import model.User;
import utilites.PasswordHasher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AuthService {
    private boolean isAuthorized;

    public boolean getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean value) {
        isAuthorized = value;
    }

    public boolean authorization(String login, String password, User user, Wallet wallet) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Path userPath = Paths.get("storage", login + ".json");

        try {
            if (Files.exists((userPath))) {
                String userInfo = Files.readString(userPath);
                JsonNode userData = mapper.readTree(userInfo);
                String hashPassword = PasswordHasher.hash(password);

                if (userData.get("hashPassword").asText().equals(hashPassword)) {
                    user.setLogin(login);

                    JsonNode walletNode = userData.get("wallet");
                    Wallet walletData = mapper.treeToValue(walletNode, Wallet.class);
                    System.out.println(walletData);
                    wallet.setCategories(walletData.getCategories());
                    wallet.setBalance(walletData.getBalance());

                    System.out.println("Авторизация прошла успешно");
                    setIsAuthorized(true);
                } else {
                    throw new IOException("Неверный пароль");
                }

            } else {
                throw new IOException("Пользователь с такими данными не найден");
            }
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                System.out.println(errorMessage);
            }
            System.out.println("Ошибка авторизации. Попробуйте еще раз");
        }
        return getIsAuthorized();
    }

    public boolean registration(String login, String password, User user, Wallet wallet) {
        UserData userData = new UserData();
        String hashPassword = PasswordHasher.hash(password);
        user.setLogin((login));
        userData.createData(user, wallet, hashPassword);

        UserDataService saveService = new UserDataService();
        boolean isDataSaved = saveService.saveNewData(userData, login);

        if (isDataSaved) {
            setIsAuthorized(true);
            System.out.println("Регистрация прошла успешно");
        }

        return isAuthorized;
    }
}
