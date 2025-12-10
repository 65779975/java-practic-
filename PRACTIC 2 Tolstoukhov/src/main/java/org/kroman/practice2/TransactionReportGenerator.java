package org.kroman.practice2;

import java.util.List;

public abstract class TransactionReportGenerator {
    private static final double VISUALIZATION_UNIT = -1000.0; // 1 зірка = 1 тисячі

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printMinMaxExpenseReport(Transaction min, Transaction max, java.time.LocalDate start, java.time.LocalDate end) {
        System.out.println("\n--- Звіт за період з " + start + " по " + end + " ---");
        if (max != null) System.out.println("Найбільша витрата: " + max.getDescription() + " (" + max.getAmount() + ")");
        else System.out.println("Найбільша витрата: не знайдено.");

        if (min != null) System.out.println("Найменша витрата: " + min.getDescription() + " (" + min.getAmount() + ")");
        else  System.out.println("Найменша витрата: не знайдено.");
    }

    public static void printCategoryExpenseReport(java.util.Map<String, Double> categorySummary) {
        System.out.println("\n--- Звіт по категоріях витрат ---");
        categorySummary.entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByValue())
                .forEach(entry -> {
                    System.out.printf("Категорія: %-20s | Сума: %.2f%n", entry.getKey(), entry.getValue());
                });
    }

    public static void printMonthlyExpenseReport(java.util.Map<String, Double> monthlySummary) {
        System.out.println("\n--- Звіт по місяцях (витрати) ---");

        monthlySummary.entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .forEach(entry -> {
                    double totalExpenses = entry.getValue();
                    long starsCount = Math.round(totalExpenses / VISUALIZATION_UNIT);
                    StringBuilder stars = new StringBuilder();
                    for (int i = 0; i < starsCount; i++) {
                        stars.append('*');
                    }
                    System.out.printf("Місяць: %-10s | Сума: %-10.2f | %s%n", entry.getKey(), totalExpenses, stars);
                });
    }
}