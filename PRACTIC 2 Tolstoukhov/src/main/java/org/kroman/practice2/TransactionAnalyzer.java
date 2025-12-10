package org.kroman.practice2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
                if (date.format(DateTimeFormatter.ofPattern("MM-yyyy")).equals(monthYear)) {
                    count++;
                }
            } catch (Exception e) {
                System.err.println("Некоректний формат дати: " + transaction.getDate());
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Transaction findMaxExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findMinExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static java.util.Map<String, Double> summarizeExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public static java.util.Map<String, Double> summarizeExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> {
                            try {
                                return LocalDate.parse(t.getDate(), DATE_FORMATTER).format(DateTimeFormatter.ofPattern("MM-yyyy"));
                            } catch (Exception e) {
                                return "Некоректний формат дати!";
                            }
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}