package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.UserData;
import model.Wallet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserDataService {
    private boolean isSaved;

    public void setIsSaved(boolean saved) {
        isSaved = saved;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public boolean saveNewData(UserData userData, String login) {
        Path userPath = Paths.get("storage", login + ".json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            if (!Files.exists((userPath))) {
                Files.createFile(userPath);
                String jsonUserData = mapper.writeValueAsString(userData);
                Files.writeString(userPath, jsonUserData);
                setIsSaved(true);
            } else {
                throw new IOException("Пользователь уже существует");
            }
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                System.out.println(errorMessage);
            }
            System.out.println("Не удалось создать аккаунт. Попробуйте еще раз");
        }
        return getIsSaved();
    }

    public static void saveData(String login, Wallet wallet) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Path userPath = Paths.get("storage", login + ".json");

        try {
            if (Files.exists((userPath))) {
                String userInfo = Files.readString(userPath);
                JsonNode userDataNode = mapper.readTree(userInfo);
                UserData userData = mapper.treeToValue(userDataNode, UserData.class);
                userData.setWallet(wallet);

                mapper.writeValue(userPath.toFile(), userData);
                System.out.println("Данные успешно сохранены");

            } else {
                throw new IOException("Пользователь с такими данными не найден");
            }
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                System.out.println(errorMessage);
            }
            System.out.println("Ошибка сохранения данных");
        }
    }
}
