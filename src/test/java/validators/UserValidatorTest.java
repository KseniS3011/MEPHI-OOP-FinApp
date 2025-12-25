package validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    @Test
    void shouldReturnTrueForValidLogin() {
        boolean result = validator.validateLogin("user123");
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForShortLogin() {
        boolean result = validator.validateLogin("ab");
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForLoginWithInvalidCharacters() {
        boolean result = validator.validateLogin("user@123");
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForEmptyLogin() {
        boolean result = validator.validateLogin("");
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueForValidPassword() {
        boolean result = validator.validatePassword("secret12");
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForShortPassword() {
        boolean result = validator.validatePassword("123");
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForEmptyPassword() {
        boolean result = validator.validatePassword("");
        assertFalse(result);
    }
}
