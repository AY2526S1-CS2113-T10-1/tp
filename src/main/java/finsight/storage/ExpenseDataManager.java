package finsight.storage;

import finsight.expense.Expense;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDataManager {
    private final Path dataPath;

    private ExpenseDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public ExpenseDataManager(String fileName) {
        this(Path.of(fileName));
    }

    private void ensureParentDir() throws IOException {
        Path parent = dataPath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private void ensureFileExist() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataPath)) {
            Files.createFile(dataPath);
        }
    }

    public ArrayList<Expense> load() throws IOException {
        ensureFileExist();
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        ArrayList<Expense> expenses = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            Expense expense = parseRecord(line);
            if (expense != null) {
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public void writeToFile(List<Expense> expenses) throws IOException {
        ensureParentDir();
        Path tmp = dataPath.resolveSibling(dataPath.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (Expense expense : expenses) {
                writer.write(formatRecord(expense));
                writer.newLine();
            }
        }
        Files.move(tmp, dataPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public void appendToFile(Expense expense) throws IOException {
        ensureFileExist();
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(formatRecord(expense));
            writer.newLine();
        }
    }

    private String formatRecord(Expense expense) {
        String description =  sanitize(expense.getDescription());
        String expenseAmount = expense.getExpenseAmount().toString();
        return String.join("|", description, expenseAmount);
    }

    private Expense parseRecord(String line) {
       String[] parts = line.split("\\|", -1);
       if (parts.length < 2) {
           return null;
       }
       String description = unsanitize(parts[0]);
       String expenseAmount = parts[1];
       return new Expense(description, expenseAmount);
    }

    private String sanitize(String line) {
        return line == null? "" : line.replace("|", "/");
    }

    private String unsanitize(String line) {
        return line;
    }
}
