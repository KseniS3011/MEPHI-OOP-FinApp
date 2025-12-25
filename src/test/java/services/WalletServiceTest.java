package services;

import model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletServiceTest {

    private WalletService walletService;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        walletService = new WalletService();
        wallet = new Wallet();
    }

    @Test
    void shouldCreateNewCategory() {
        boolean result = walletService.createCategory("Еда", wallet);

        assertTrue(result);
        assertEquals(2, wallet.getCategories().size());
    }

    @Test
    void shouldNotCreateDuplicateCategory() {
        walletService.createCategory("Еда", wallet);

        boolean result = walletService.createCategory("Еда", wallet);

        assertFalse(result);
    }

    @Test
    void shouldNotDeleteDefaultCategory() {
        boolean result = walletService.deleteCategory("1", wallet);

        assertFalse(result);
    }

    @Test
    void shouldDeleteExistingCategory() {
        walletService.createCategory("Транспорт", wallet);

        boolean result = walletService.deleteCategory("2", wallet);

        assertTrue(result);
        assertEquals(1, wallet.getCategories().size());
    }

    @Test
    void shouldAddIncome() {
        boolean result = walletService.addIncome(wallet, "1", 1000);

        assertTrue(result);
        assertEquals(1000, wallet.getBalance());
        assertEquals(1000, wallet.getTotalIncome());
    }

    @Test
    void shouldAddExpense() {
        Wallet wallet = new Wallet();
        WalletService service = new WalletService();

        wallet.setBalance(100);

        service.createCategory("Food", wallet);

        String categoryIndex = wallet.getCategories().keySet()
                .stream()
                .filter(k -> wallet.getCategories().get(k).getName().equals("Food"))
                .findFirst()
                .orElseThrow();

        wallet.getCategories().get(categoryIndex).setLimit(1000);

        boolean result = service.addExpenses(wallet, categoryIndex, 50);

        assertTrue(result);
        assertEquals(50, wallet.getBalance());
        assertEquals(50, wallet.getTotalExpenses());
    }

    @Test
    void shouldNotAllowExpenseGreaterThanBalance() {
        boolean result = walletService.addExpenses(wallet, "1", 500);

        assertFalse(result);
        assertEquals(0, wallet.getBalance());
    }
}