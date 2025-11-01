package finsight.storage;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.storage.exceptions.AmountPersistCorruptedException;

import java.nio.file.Path;

/**
 * Persists and retrieves {@link Income} records from a text-based storage file.
 *
 * <p><strong>Record format (per line)</strong>:
 * <code>description|amount</code>
 * where:
 * <ul>
 *   <li><code>description</code> — textual description of the income
 *       (sanitized using {@link DataManager#sanitize(String)} to prevent delimiter conflicts)</li>
 *   <li><code>amount</code> — numeric value of the income amount stored as a string</li>
 * </ul>
 *
 * <p>This class focuses solely on I/O management for income data while delegating
 * validation and domain-specific parsing to the {@link Income} class.
 * It provides consistent storage and retrieval using the {@link DataManager} abstraction.</p>
 *
 * @author Royden Lim Yi Ren
 * @see DataManager
 * @since 15 Oct 2025
 */
public class IncomeDataManager extends DataManager<Income, Exception> {
    private static final String INCOME = "income";
    /**
     * Path to the file storing income records.
     */
    private final Path dataPath;

    /**
     * Creates an instance of {@code IncomeDataManager} with a specific {@link Path}.
     *
     * @param dataPath path to the income data file
     */
    private IncomeDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Creates an instance of {@code IncomeDataManager} from a file name.
     *
     * @param fileName name of the data file (absolute or relative)
     */
    public IncomeDataManager(String fileName) {
        this(Path.of(fileName));
    }

    /**
     * Returns the path to the income data file.
     *
     * @return the data file path
     */
    @Override
    protected Path dataFilePath() {
        return dataPath;
    }

    /**
     * Converts an {@link Income} object into a formatted string suitable for file storage.
     * Each field is separated by {@code "|"}.
     *
     * @param income the income record to format
     * @return the formatted line representation of the income record
     */
    @Override
    protected String formatRecord(Income income) {
        String description = sanitize(income.getDescription());
        String incomeAmount = Float.toString(income.getAmountEarned());
        return String.join("|", description, incomeAmount);
    }

    /**
     * Parses a line from the data file into an {@link Income} object.
     * Lines that do not contain both required fields are ignored by returning {@code null}.
     *
     * @param line a single line read from the data file
     * @return a parsed {@link Income} record, or {@code null} if the line is malformed
     * @throws AddIncomeCommandWrongFormatException if the content cannot be converted
     *                                              into a valid {@link Income} object (e.g., invalid numeric format)
     */
    @Override
    protected Income parseRecord(String line)
            throws AmountPersistCorruptedException, AddIncomeCommandWrongFormatException {
        String[] parts = line.split(FIELD_DELIMITER, SPLIT_KEEP_EMPTY_FIELDS);
        if (parts.length < 2) {
            return null;
        }

        return parseIncome(parts);
    }

    private Income parseIncome(String[] parts)
            throws AmountPersistCorruptedException, AddIncomeCommandWrongFormatException {
        String description = unsanitize(parts[0]);
        String incomeAmount = parts[1];
        float amount;

        try {
            amount = Float.parseFloat(incomeAmount);
        } catch (NumberFormatException e) {
            throw new AmountPersistCorruptedException(incomeAmount, INCOME);
        }

        if (amount <= 0) {
            throw new AmountPersistCorruptedException(incomeAmount, INCOME);
        }

        return new Income(description, incomeAmount);
    }
}
