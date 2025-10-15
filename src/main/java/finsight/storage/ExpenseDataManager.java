package finsight.storage;

import finsight.expense.Expense;

import java.nio.file.Path;

public class ExpenseDataManager extends DataManager<Expense, Exception> {
    private final Path dataPath;

    private ExpenseDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public ExpenseDataManager(String fileName) {
        this(Path.of(fileName));
    }

    protected Path dataFilePath() {
        return dataPath;
    }

    protected String formatRecord(Expense expense) {
        String description =  sanitize(expense.getDescription());
        String expenseAmount = expense.getExpenseAmount().toString();
        return String.join("|", description, expenseAmount);
    }

    protected Expense parseRecord(String line) {
       String[] parts = line.split("\\|", -1);
       if (parts.length < 2) {
           return null;
       }
       String description = unsanitize(parts[0]);
       String expenseAmount = parts[1];
       return new Expense(description, expenseAmount);
    }
}
