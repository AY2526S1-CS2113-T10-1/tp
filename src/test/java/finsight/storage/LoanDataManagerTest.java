package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import finsight.loan.Loan;
import finsight.storage.exceptions.AmountPersistCorruptedException;
import finsight.storage.exceptions.DatePersistCorruptedException;
import finsight.storage.exceptions.DayOfInvestPersistCorruptedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Verifies persistence and parsing behavior of {@link LoanDataManager} for {@link Loan} records.
 *
 * <p>This suite covers:
 * <ul>
 *   <li>Field joining and delimiter sanitization in {@code formatRecord}.</li>
 *   <li>Parsing of well-formed lines, including repayment flag handling.</li>
 *   <li>Null on malformed field counts to allow caller-side skipping.</li>
 *   <li>Write → read round-trip and append ordering with UTF-8 verification.</li>
 *   <li>Graceful empty result on any parse failure during bulk load.</li>
 * </ul>
 * </p>
 *
 * @author Royden Lim Yi Ren
 * @version 1.0
 * @see LoanDataManager
 * @see Loan
 * @since 13 Oct 2025
 */
final class LoanDataManagerTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @TempDir
    Path tempDir;

    private Path dataFile;
    private LoanDataManager dataManager;

    /**
     * Prepares a fresh {@link LoanDataManager} instance targeting a new file
     * within the JUnit-provided temporary directory.
     */
    @BeforeEach
    void setUp() {
        dataFile = tempDir.resolve("TestLoan.txt");
        dataManager = new LoanDataManager(dataFile.toString());
    }

    /**
     * Ensures {@link LoanDataManager#formatRecord(Loan)} joins fields correctly
     * and sanitizes reserved characters in description. Also verifies that a
     * repaid loan is encoded with flag {@code 1}.
     */
    @Test
    void formatRecord_joinsField_andSanitizesDescription() {
        Loan loan = new Loan("Poop|Food", "69.69", "10-10-2025 23:59");
        loan.setRepaid();

        String record = dataManager.formatRecord(loan);
        assertEquals("1|Poop%7CFood|69.69|10-10-2025 23:59", record);
    }

    /**
     * Verifies that a not-repaid loan is encoded with flag {@code 0} and that all
     * fields are joined in the expected order.
     */
    @Test
    void formatRecord_handlesNotRepaidFlag() {
        Loan loan = new Loan("PoopieHead", "126.69", "10-10-2025 12:21");

        String record = dataManager.formatRecord(loan);
        assertEquals("0|PoopieHead|126.69|10-10-2025 12:21", record);
    }

    /**
     * Confirms that {@link LoanDataManager#parseRecord(String)} parses a well-formed
     * line, sets repayment status based on the encoded flag, and unsanitizes fields.
     */
    @Test
    void parseRecord_parseWellFormedLine_andSetsRepaidStatus()
            throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String record = "1|Eat%7CPoop|69.126|10-10-2025 23:59";
        Loan loan = dataManager.parseRecord(record);

        assertNotNull(loan);
        assertTrue(loan.isRepaid(), "Repaid flag '1' should set loan as repaid");
        assertEquals("Eat|Poop", loan.getDescription());
        assertEquals("69.126", loan.getAmountLoaned().toString());
        assertEquals("10-10-2025 23:59", loan.getLoanReturnDate().format(FORMATTER));
    }

    /**
     * Verifies not-repaid parsing and field values for a valid input line.
     */
    @Test
    void parseRecord_parsesNotRepaid_andFields()
            throws AmountPersistCorruptedException, DayOfInvestPersistCorruptedException {
        String record = "0|Buy Poop|200|10-10-2025 00:00";

        Loan loan = dataManager.parseRecord(record);

        assertNotNull(loan);
        assertFalse(loan.isRepaid());
        assertEquals("Buy Poop", loan.getDescription());
        assertEquals("200.0", loan.getAmountLoaned().toString());
        assertEquals("10-10-2025 00:00", loan.getLoanReturnDate().format(FORMATTER));
    }

    /**
     * Ensures malformed lines (wrong field count) return {@code null} so the caller
     * can skip invalid records gracefully.
     */
    @Test
    void parseRecord_returnsNull_whenFieldCountIsWrong()
            throws AmountPersistCorruptedException, DatePersistCorruptedException {
        String record = "0|not enough|field";
        Loan loan = dataManager.parseRecord(record);

        assertNull(loan, "Manager should return null for non-4-field lines so caller can skip them");
    }

    /**
     * Performs a write → read round-trip to confirm that serialized {@link Loan}
     * records are persisted in UTF-8 and deserialized without loss or corruption,
     * including repayment flags and timestamps.
     *
     * @throws IOException if disk verification or read-back fails
     * @see LoanDataManager#writeToFile(List)
     * @see LoanDataManager#tryLoad()
     */
    @Test
    void writeToFile_tryLoadRoundTrip() throws IOException {
        Loan loan1 = new Loan("Buy|Poop", "69.69", "10-10-2025 23:59");
        Loan loan2 = new Loan("Eat POOP", "200", "10-10-2025 12:21");
        loan2.setRepaid();

        dataManager.writeToFile(List.of(loan1, loan2));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "0|Buy%7CPoop|69.69|10-10-2025 23:59",
                "1|Eat POOP|200.0|10-10-2025 12:21"
        ), records);

        var loaded = dataManager.tryLoad();
        assertEquals(2, loaded.size());

        Loan loanOne = loaded.get(0);
        assertFalse(loanOne.isRepaid());
        assertEquals("Buy|Poop",  loanOne.getDescription());
        assertEquals("69.69", loanOne.getAmountLoaned().toString());
        assertEquals("10-10-2025 23:59", loanOne.getLoanReturnDate().format(FORMATTER));

        Loan loanTwo = loaded.get(1);
        assertTrue(loanTwo.isRepaid());
        assertEquals("Eat POOP", loanTwo.getDescription());
        assertEquals("200.0", loanTwo.getAmountLoaned().toString());
        assertEquals("10-10-2025 12:21", loanTwo.getLoanReturnDate().format(FORMATTER));
    }

    /**
     * Confirms that {@link LoanDataManager#appendToFile(Loan)} preserves record
     * order by appending new lines to the end of the file.
     *
     * @throws IOException if disk verification fails
     */
    @Test
    void appendToFile_appendsInOrder() throws IOException {
        Loan loan1 = new Loan("Poop Poop", "1", "10-10-2025 00:00");
        Loan loan2 = new Loan("NOT ENOUGH POOP", "0",  "10-10-2025 23:59");

        dataManager.writeToFile(List.of(loan1));
        dataManager.appendToFile(loan2);

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "0|Poop Poop|1.0|10-10-2025 00:00",
                "0|NOT ENOUGH POOP|0.0|10-10-2025 23:59"
        ), records);
    }

    /**
     * Verifies that {@link LoanDataManager#tryLoad()} skips corrupted records
     * while successfully loading valid ones from the same data file.
     *
     * <p>This test writes two records: one valid loan entry and one malformed entry
     * with non-numeric and invalid date fields. The method under test should
     * load only the valid record and skip the corrupted one, demonstrating
     * fault-tolerant behavior during bulk load operations.</p>
     *
     * @throws IOException if an I/O error occurs while writing the test data file
     */
    @Test
    void tryLoad_returnsEmpty_onParsingFailure() throws IOException {
        Files.writeString(dataFile,
                "0|BadFormat|60|10-10-2025 23:59\n" +
                "0|Poop Brain|no number|invalid Date", StandardCharsets.UTF_8);

        var records = dataManager.tryLoad();
        assertTrue(records.size() == 1, "Any parse exception during load will be skipped.");
    }
}
