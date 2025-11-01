package finsight.storage;

import finsight.expense.Expense;
import finsight.storage.exceptions.AmountPersistCorruptedException;

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
    private static final String EXPENSE = "expense";
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
    protected Expense parseRecord(String line) throws AmountPersistCorruptedException {
        String[] parts = line.split(FIELD_DELIMITER, SPLIT_KEEP_EMPTY_FIELDS);
        if (parts.length < 2) {
            return null;
        }
        return parseExpense(parts);
    }

    /**
     * Parses a serialized expense record into an {@link Expense} object.
     *
     * <p>This method extracts the description and amount fields from the given
     * array of string parts, performs numeric validation, and constructs an
     * {@code Expense} instance if the data is valid.</p>
     *
     * <p>Specifically:
     * <ul>
     *   <li>The first element ({@code parts[0]}) is unsanitized and treated as the expense description.</li>
     *   <li>The second element ({@code parts[1]}) is parsed as a {@code double} amount.</li>
     *   <li>If the amount is non-numeric or non-positive, an
     *       {@link finsight.storage.exceptions.AmountPersistCorruptedException}
     *       is thrown to indicate corrupted persisted data.</li>
     * </ul>
     *
     * @param parts the tokenized fields of a serialized expense line, expected to contain
     *              the description and amount in that order
     * @return a valid {@link Expense} object created from the given parts
     * @throws AmountPersistCorruptedException if the amount field is not numeric or ≤ 0
     */
    private Expense parseExpense(String[] parts) throws AmountPersistCorruptedException {
        String description = unsanitize(parts[0]);
        String expenseAmount = parts[1];
        double amount;

        try {
            amount = Double.parseDouble(expenseAmount);
        } catch (NumberFormatException e) {
            throw new AmountPersistCorruptedException(expenseAmount, EXPENSE);
        }

        if (amount <= 0) {
            throw new AmountPersistCorruptedException(expenseAmount, EXPENSE);
        }

        return new Expense(description, expenseAmount);
    }
}
