package org.kroman.practice2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        if (transactions.isEmpty()) {
            System.out.println("Не вдалося завантажити транзакції або файл порожній.");
            return;
        }

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        TransactionReportGenerator.printBalanceReport(totalBalance);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        Transaction maxExpense = TransactionAnalyzer.findMaxExpense(transactions, startDate, endDate);
        Transaction minExpense = TransactionAnalyzer.findMinExpense(transactions, startDate, endDate);

        Map<String, Double> categoryReport = TransactionAnalyzer.summarizeExpensesByCategory(transactions);
        Map<String, Double> monthlyReport = TransactionAnalyzer.summarizeExpensesByMonth(transactions);

        TransactionReportGenerator.printMinMaxExpenseReport(minExpense, maxExpense, startDate, endDate);
        TransactionReportGenerator.printCategoryExpenseReport(categoryReport);
        TransactionReportGenerator.printMonthlyExpenseReport(monthlyReport);
    }
}