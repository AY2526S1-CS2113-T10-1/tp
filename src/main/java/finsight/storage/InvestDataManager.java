package finsight.storage;

import finsight.investment.Investment;
import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;

import java.nio.file.Path;

/**
 * Persists and retrieves {@link Investment} records from a text-based data file.
 *
 * <p><strong>Record format (per line)</strong>:
 * <code>description|amount|dayOfInvest</code>
 * where:
 * <ul>
 *   <li><code>description</code> — textual description of the investment
 *       (sanitized using {@link DataManager#sanitize(String)} to prevent delimiter conflicts)</li>
 *   <li><code>amount</code> — numeric value of the investment amount stored as a string</li>
 *   <li><code>dayOfInvest</code> — integer representing the day of the month the investment occurs</li>
 * </ul>
 *
 * <p>This class manages I/O operations for investments while delegating
 * data validation to the {@link Investment} class and its associated
 * exceptions. It ensures consistent serialization and deserialization
 * of investment data across program executions.</p>
 *
 * @author Royden Lim Yi Ren
 * @see DataManager
 * @see Investment
 * @since 15 Oct 2025
 */
public class InvestDataManager extends DataManager<Investment, Exception> {

    /**
     * Path to the data file storing investment records.
     */
    private final Path dataPath;

    /**
     * Creates an {@code InvestDataManager} instance with a specific {@link Path}.
     *
     * @param dataPath path to the investment data file
     */
    private InvestDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Creates an {@code InvestDataManager} instance from a file name.
     *
     * @param fileName name of the investment data file (absolute or relative)
     */
    public InvestDataManager(String fileName) {
        this(Path.of(fileName));
    }

    /**
     * Returns the path to the investment data file.
     *
     * @return the {@link Path} to the data file
     */
    @Override
    protected Path dataFilePath() {
        return dataPath;
    }

    /**
     * Converts an {@link Investment} object into a line suitable for file storage.
     * Each field is separated by the pipe symbol ({@code "|"}).
     *
     * @param investment the investment record to format
     * @return the formatted string representation of the investment
     */
    @Override
    protected String formatRecord(Investment investment) {
        String description = sanitize(investment.getDescription());
        String investAmount = investment.getInvestmentAmount().toString();
        String rateOfReturn = investment.getInvestmentReturnRate().toString();
        String dayOfInvest = Integer.toString(investment.getInvestmentDateOfMonth());
        return String.join("|", description, investAmount, rateOfReturn, dayOfInvest);
    }

    /**
     * Parses a line from the data file into an {@link Investment} object.
     * Lines with missing fields are ignored by returning {@code null}.
     *
     * @param line the raw line read from the data file
     * @return a parsed {@link Investment} instance, or {@code null} if malformed
     * @throws AddInvestmentWrongNumberFormatException if the amount field cannot be parsed as a valid number
     * @throws AddInvestmentDateOutOfBoundsException   if the day of month is not within valid bounds (1–31)
     */
    @Override
    protected Investment parseRecord(String line)
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }
        String description = sanitize(parts[0]);
        String investAmount = parts[1];
        String rateOfReturn = parts[2];
        String dayOfInvest = parts[3];
        return new Investment(description, investAmount, rateOfReturn, dayOfInvest);
    }
}
