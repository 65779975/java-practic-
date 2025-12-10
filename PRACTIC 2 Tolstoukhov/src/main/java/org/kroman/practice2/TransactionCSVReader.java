package org.kroman.practice2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {
    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 3) {
                        transactions.add(new Transaction(values[0], Double.parseDouble(values[1]), values[2]));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Помилка під час читання або парсингу файлу: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }
}
