package finsight.storage;

import finsight.loan.Loan;
import finsight.storage.exceptions.AmountPersistCorruptedException;
import finsight.storage.exceptions.DatePersistCorruptedException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    private static final String LOAN = "loan";
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
     * @throws AmountPersistCorruptedException if the amount is not numeric or ≤ 0
     * @throws DatePersistCorruptedException if the due date cannot be parsed using the expected format
     */
    @Override
    protected Loan parseRecord(String line) throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String[] parts = line.split(FIELD_DELIMITER, SPLIT_KEEP_EMPTY_FIELDS);
        if (parts.length < 4) {
            return null;
        }
        boolean isRepaid = parts[0].equals("1");
        Loan loan = parseLoan(parts);
        if (isRepaid) {
            loan.setRepaid();
        }
        return loan;
    }

    /**
     * Parses a serialized loan record into a {@link Loan} object.
     *
     * <p>This method reconstructs a loan entry from its serialized string fields
     * obtained from persistent storage. It performs field extraction, type parsing,
     * and validation to ensure the loan data is numerically and temporally valid
     * before constructing a {@code Loan} instance.</p>
     *
     * <p>Specifically:
     * <ul>
     *   <li>The second element ({@code parts[1]}) represents the loan description
     *       and is decoded via {@link #unsanitize(String)} to restore original characters.</li>
     *   <li>The third element ({@code parts[2]}) represents the loan amount, which
     *       is parsed as a {@code double} and validated to be positive.</li>
     *   <li>The fourth element ({@code parts[3]}) represents the due date/time,
     *       which is parsed using {@link #FORMATTER} to ensure it matches the
     *       expected {@code dd-MM-yyyy HH:mm} format.</li>
     *   <li>If any parsing or validation fails, an appropriate persistence-related
     *       exception is thrown to indicate corrupted stored data.</li>
     * </ul>
     *
     * @param parts the tokenized fields of a serialized loan record, expected to contain
     *              a repaid flag, description, amount, and due date in that order
     * @return a valid {@link Loan} object created from the provided fields
     *
     * @throws AmountPersistCorruptedException if the amount is not numeric or ≤ 0
     * @throws DatePersistCorruptedException if the due date cannot be parsed using the expected format
     */
    private Loan parseLoan(String[] parts) throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String description = unsanitize(parts[1]);
        String loanAmount = parts[2];
        String returnBy = parts[3];
        double amount;

        try {
            amount = Double.parseDouble(loanAmount);
        } catch (NumberFormatException e) {
            throw new AmountPersistCorruptedException(loanAmount, LOAN);
        }

        if (amount <= 0) {
            throw new AmountPersistCorruptedException(loanAmount, LOAN);
        }

        try {
            LocalDateTime.parse(returnBy, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DatePersistCorruptedException(returnBy);
        }

        return new Loan(description, loanAmount, returnBy);
    }
}
