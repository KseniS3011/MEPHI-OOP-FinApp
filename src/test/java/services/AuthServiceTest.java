package services;

import model.User;
import model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;
    private User user;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        user = new User();
        wallet = new Wallet();
    }

    @BeforeEach
    void cleanStorage() throws IOException {
        Path storage = Paths.get("storage");

        Files.createDirectories(storage);

        if (Files.exists(storage)) {
            Files.list(storage)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    @Test
    void shouldAuthorizeWithValidLoginAndPassword() {
        authService.setIsAuthorized(false);
        boolean result = authService.registration("user123", "password123", user, wallet);

        assertTrue(result);
        assertEquals("user123", user.getLogin());
    }

    @Test
    void shouldSetAuthorizedFlagAfterSuccess() {
        authService.setIsAuthorized(false);
        authService.registration("user123", "password123", user, wallet);

        assertTrue(authService.getIsAuthorized());
    }
}
