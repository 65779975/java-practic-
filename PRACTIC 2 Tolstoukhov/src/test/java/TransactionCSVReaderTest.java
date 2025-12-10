import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kroman.practice2.Transaction;
import org.kroman.practice2.TransactionCSVReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class TransactionCSVReaderTest {

    @Test
    void testReadTransactionsFromDynamicCSV() throws IOException {
        Path tempFile = Files.createTempFile("transactions_", ".csv");

        String csvData = """
                05-12-2023,-7850,Сільпо
                07-12-2023,-1200,Аптека
                10-12-2023,80000,Зарплата
                12-12-2023,1500,Авторські винагороди
                14-12-2023,-3200,Комунальні послуги
                """;
        Files.writeString(tempFile, csvData);

        List<Transaction> transactions = TransactionCSVReader.readTransactions(tempFile.toUri().toString());

        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій порожній");

        Transaction firstTransaction = transactions.get(0);
        Assertions.assertEquals("05-12-2023", firstTransaction.getDate());
        Assertions.assertEquals(-7850, firstTransaction.getAmount());
        Assertions.assertEquals("Сільпо", firstTransaction.getDescription());

        Files.deleteIfExists(tempFile);
    }
}
