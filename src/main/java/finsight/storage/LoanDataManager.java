package finsight.storage;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

/**
 * Persists and retrieves {@link Loan} records from a line-oriented text file.
 *
 * <p><strong>Record format (per line)</strong>:
 * <code>repaid|description|amount|returnBy</code>
 * where:
 * <ul>
 *   <li><code>repaid</code> — {@code "1"} if repaid, {@code "0"} otherwise</li>
 *   <li><code>description</code> — loan description (delimiters sanitized by
 *       {@link DataManager#sanitize(String)})</li>
 *   <li><code>amount</code> — original string value supplied to {@link Loan}</li>
 *   <li><code>returnBy</code> — due date/time formatted as {@link #FORMATTER}</li>
 * </ul>
 *
 * <p>This class focuses on I/O and record formatting; domain validation is delegated
 * to {@link Loan} construction and related logic.</p>
 *
 * @author Royden Lim Yi Ren
 *
 * @since 15 Oct 2025
 */
public class LoanDataManager extends DataManager<Loan, AddLoanCommandWrongFormatException> {

    /**
     * Date/time pattern used when formatting and parsing {@code returnBy}.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Absolute or relative path to the data file.
     */
    private final Path dataPath;

    /**
     * Creates a manager that stores data at the given path.
     *
     * @param dataPath path to the backing data file
     */
    private LoanDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Creates a manager that stores data at the given file name.
     *
     * @param fileName file name (absolute or relative) of the backing data file
     */
    public LoanDataManager(String fileName) {
        this(Path.of(fileName));
    }

    /**
     * Returns the path of the loans data file.
     *
     * @return data file path
     */
    @Override
    protected Path dataFilePath() {
        return dataPath;
    }

    /**
     * Formats a {@link Loan} into a single line for storage.
     *
     * <p>Fields are joined using {@code "|"} and the description is sanitized to
     * prevent delimiter conflicts.</p>
     *
     * @param loan loan to format
     * @return serialized line following the record format
     */
    @Override
    protected String formatRecord(Loan loan) {
        String repaid = loan.isRepaid() ? "1" : "0";
        String description = sanitize(loan.getDescription());
        String loanAmount = loan.getAmountLoaned().toString();
        String returnBy = loan.getLoanReturnDate().format(FORMATTER);
        return String.join("|", repaid, description, loanAmount, returnBy);
    }

    /**
     * Parses a stored line into a {@link Loan}.
     *
     * <p>Lines not matching the expected 4-field format are ignored by returning
     * {@code null} so callers can safely skip them.</p>
     *
     * @param line serialized line from the data file
     * @return parsed {@link Loan}, or {@code null} if the line is malformed
     * @throws AddLoanCommandWrongFormatException if the content is syntactically correct
     *                                            but cannot be mapped to a valid {@link Loan}
     *                                            (e.g., invalid date/amount)
     */
    @Override
    protected Loan parseRecord(String line) throws AddLoanCommandWrongFormatException {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        boolean isRepaid = parts[0].equals("1");
        String description = unsanitize(parts[1]);
        String loanAmount = parts[2];
        String returnBy = parts[3];
        Loan loan = new Loan(description, loanAmount, returnBy);
        if (isRepaid) {
            loan.setRepaid();
        } else {
            loan.setNotRepaid();
        }
        return loan;
    }
}
