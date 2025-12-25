package utilites;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {

    @Test
    void shouldReturnHashForPassword() {
        String hash = PasswordHasher.hash("password123");

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void samePasswordShouldProduceSameHash() {
        String hash1 = PasswordHasher.hash("password123");
        String hash2 = PasswordHasher.hash("password123");

        assertEquals(hash1, hash2);
    }

    @Test
    void differentPasswordsShouldProduceDifferentHashes() {
        String hash1 = PasswordHasher.hash("password123");
        String hash2 = PasswordHasher.hash("anotherPassword");

        assertNotEquals(hash1, hash2);
    }
}
