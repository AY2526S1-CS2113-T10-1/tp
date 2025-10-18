package finsight.storage;

import finsight.expense.Expense;

import java.nio.file.Path;

/**
 * Persists and retrieves {@link Expense} records from a text-based storage file.
 *
 * <p><strong>Record format (per line)</strong>:
 * <code>description|amount</code>
 * where:
 * <ul>
 *   <li><code>description</code> — textual description of the expense
 *       (sanitized using {@link DataManager#sanitize(String)} to avoid delimiter conflicts)</li>
 *   <li><code>amount</code> — original string value of the expense amount</li>
 * </ul>
 *
 * <p>This class provides file I/O for expenses while delegating validation and parsing
 * of domain data to the {@link Expense} class. It ensures consistent storage and retrieval
 * of expense records in the {@code data} directory.</p>
 *
 * @author Royden Lim Yi Ren
 * @see DataManager
 * @since 15 Oct 2025
 */
public class ExpenseDataManager extends DataManager<Expense, Exception> {

    /**
     * Path to the underlying data file where expense records are stored.
     */
    private final Path dataPath;

    /**
     * Creates an instance of {@code ExpenseDataManager} with a specific data file path.
     *
     * @param dataPath path to the expense data file
     */
    private ExpenseDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Creates an instance of {@code ExpenseDataManager} from a file name.
     *
     * @param fileName name of the data file (absolute or relative)
     */
    public ExpenseDataManager(String fileName) {
        this(Path.of(fileName));
    }

    /**
     * Returns the path to the expense data file.
     *
     * @return the data file path
     */
    @Override
    protected Path dataFilePath() {
        return dataPath;
    }

    /**
     * Converts an {@link Expense} record into a line suitable for writing to file.
     * Each field is separated by {@code "|"}.
     *
     * @param expense the expense record to format
     * @return the formatted string representation of the expense
     */
    @Override
    protected String formatRecord(Expense expense) {
        String description = sanitize(expense.getDescription());
        String expenseAmount = expense.getExpenseAmount().toString();
        return String.join("|", description, expenseAmount);
    }

    /**
     * Parses a line from the data file into an {@link Expense} object.
     * Lines with insufficient fields are ignored by returning {@code null}.
     *
     * @param line the raw line read from the data file
     * @return a parsed {@link Expense} instance, or {@code null} if malformed
     */
    @Override
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
