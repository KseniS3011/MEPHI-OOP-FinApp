package services;

import model.User;
import model.UserData;
import model.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataServiceTest {

    @Test
    void shouldCreateUserDataObject() {
        User user = new User();
        user.setLogin("test");

        Wallet wallet = new Wallet();
        UserData userData = new UserData();

        userData.createData(user, wallet, "hash");

        assertNotNull(userData.getUser());
        assertNotNull(userData.getWallet());
        assertEquals("hash", userData.getHashPassword());
    }
}
