import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kroman.practice2.Transaction;
import org.kroman.practice2.TransactionAnalyzer;

import java.time.LocalDate;
import java.util.*;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        Transaction t1 = new Transaction("01-02-2023", 100.0, "Дохід 1");
        Transaction t2 = new Transaction("15-02-2023", -50.0, "Витрата 1");
        Transaction t3 = new Transaction("05-03-2023", 150.0, "Дохід 2");
        Transaction t4 = new Transaction("20-02-2023", -30.0, "Витрата 2");
        Transaction t5 = new Transaction("25-03-2023", -70.0, "Магазин");
        testTransactions = Arrays.asList(t1, t2, t3, t4, t5);
    }

    @Test
    void testCalculateTotalBalance() {
        double expectedBalance = 100.0 - 50.0 + 150.0 - 30.0 - 70.0;
        double actualBalance = TransactionAnalyzer.calculateTotalBalance(testTransactions);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void testCountTransactionsByMonth() {
        int expectedFebCount = 3;
        int actualFebCount = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2023");
        Assertions.assertEquals(expectedFebCount, actualFebCount);
        int expectedMarCount = 2;
        int actualMarCount = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "03-2023");
        Assertions.assertEquals(expectedMarCount, actualMarCount);
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(testTransactions);
        Assertions.assertTrue(topExpenses.stream().allMatch(t -> t.getAmount() < 0));
        Assertions.assertEquals(3, topExpenses.size());
        Assertions.assertEquals(-70.0, topExpenses.get(0).getAmount());
    }

    @Test
    void testFindMaxExpenseInPeriod() {
        LocalDate start = LocalDate.of(2023, 2, 1);
        LocalDate end = LocalDate.of(2023, 3, 31);
        Transaction maxExpense = TransactionAnalyzer.findMaxExpense(testTransactions, start, end);
        Assertions.assertEquals(-70.0, maxExpense.getAmount());
    }

    @Test
    void testFindMinExpenseInPeriod() {
        LocalDate start = LocalDate.of(2023, 2, 1);
        LocalDate end = LocalDate.of(2023, 3, 31);
        Transaction minExpense = TransactionAnalyzer.findMinExpense(testTransactions, start, end);
        Assertions.assertEquals(-30.0, minExpense.getAmount());
    }

    @Test
    void testSummarizeExpensesByCategory() {
        Map<String, Double> summary = TransactionAnalyzer.summarizeExpensesByCategory(testTransactions);
        Assertions.assertEquals(-50.0, summary.get("Витрата 1"));
        Assertions.assertEquals(-30.0, summary.get("Витрата 2"));
        Assertions.assertEquals(-70.0, summary.get("Магазин"));
    }

    @Test
    void testSummarizeExpensesByMonth() {
        Map<String, Double> monthly = TransactionAnalyzer.summarizeExpensesByMonth(testTransactions);
        Assertions.assertEquals(-80.0, monthly.get("02-2023"));
        Assertions.assertEquals(-70.0, monthly.get("03-2023"));
    }

    @Test
    void testEmptyTransactionsList() {
        List<Transaction> emptyList = Collections.emptyList();
        double total = TransactionAnalyzer.calculateTotalBalance(emptyList);
        Assertions.assertEquals(0, total);
    }

    @Test
    void testInvalidDateFormatHandled() {
        List<Transaction> invalidList = Arrays.asList(
                new Transaction("32-13-2023", -100.0, "Помилка дати"),
                new Transaction("01-01-2023", -200.0, "Коректна")
        );
        int count = TransactionAnalyzer.countTransactionsByMonth(invalidList, "01-2023");
        Assertions.assertEquals(1, count);
    }

    @Test
    void testTopExpensesContainsOnlyNegatives() {
        List<Transaction> mixed = Arrays.asList(
                new Transaction("01-01-2023", 200.0, "Дохід"),
                new Transaction("02-01-2023", -300.0, "Витрата")
        );
        List<Transaction> result = TransactionAnalyzer.findTopExpenses(mixed);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getAmount() < 0);
    }

    @Test
    void testFindMaxExpenseWhenNoExpensesInPeriod() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        Transaction maxExpense = TransactionAnalyzer.findMaxExpense(testTransactions, start, end);
        Assertions.assertNull(maxExpense);
    }

    @Test
    void testFindTopExpensesWithOnlyIncome() {
        List<Transaction> onlyIncome = Arrays.asList(
                new Transaction("01-01-2023", 500.0, "Зарплата"),
                new Transaction("02-01-2023", 100.0, "Бонус")
        );
        List<Transaction> top = TransactionAnalyzer.findTopExpenses(onlyIncome);
        Assertions.assertTrue(top.isEmpty());
    }

    @Test
    void testSummarizeExpensesByCategoryWithDuplicates() {
        List<Transaction> list = Arrays.asList(
                new Transaction("01-01-2023", -100.0, "Їжа"),
                new Transaction("02-01-2023", -50.0, "Їжа"),
                new Transaction("03-01-2023", -200.0, "Одяг")
        );
        Map<String, Double> summary = TransactionAnalyzer.summarizeExpensesByCategory(list);
        Assertions.assertEquals(-150.0, summary.get("Їжа"));
        Assertions.assertEquals(-200.0, summary.get("Одяг"));
    }
}
