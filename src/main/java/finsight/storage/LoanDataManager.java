package finsight.storage;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.storage.exceptions.AmountPersistCorruptedException;
import finsight.storage.exceptions.DatePersistCorruptedException;
import finsight.ui.Ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
 * @since 15 Oct 2025
 */
public class LoanDataManager extends DataManager<Loan, Exception> {

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
     */
    @Override
    protected Loan parseRecord(String line) throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String[] parts = line.split(FIELD_DELIMITER, SPLIT_KEEP_EMPTY_FIELDS);
        if (parts.length != 4) {
            return null;
        }
        boolean isRepaid = parts[0].equals("1");
        Loan loan = parseLoan(parts);
        if (isRepaid) {
            loan.setRepaid();
        }
        return loan;
    }

    private Loan parseLoan(String[] parts) throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String description = unsanitize(parts[1]);
        String loanAmount = parts[2];
        String returnBy = parts[3];
        double amount;

        try {
            amount = Double.parseDouble(loanAmount);
        } catch (NumberFormatException e) {
            throw new AmountPersistCorruptedException(loanAmount);
        }

        if (amount <= 0) {
            throw new AmountPersistCorruptedException(loanAmount);
        }

        try {
            LocalDateTime.parse(returnBy, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DatePersistCorruptedException(returnBy);
        }

        return new Loan(description, loanAmount, returnBy);
    }
}
