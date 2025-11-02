package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.storage.exceptions.AmountPersistCorruptedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies the behavior of {@link IncomeDataManager} for reading and writing
 * {@link Income} records to text files.
 *
 * <p>This suite checks the correctness of record serialization, sanitization,
 * and deserialization, ensuring that data integrity and exception handling
 * conform to the contract defined by {@code DataManager}. Each test uses an
 * isolated temporary directory to avoid state leakage across runs.</p>
 *
 * <ul>
 *   <li>Validates sanitization and delimiter joining in {@code formatRecord}.</li>
 *   <li>Confirms correct parsing of well-formed and malformed records.</li>
 *   <li>Checks that invalid numeric fields raise an
 *       {@link AddIncomeCommandWrongFormatException}.</li>
 *   <li>Ensures successful end-to-end round-trip persistence (write → read).</li>
 * </ul>
 *
 * @author Royden Lim Yi Ren
 * @version 1.0
 * @see IncomeDataManager
 * @see Income
 * @since 13 Oct 2025
 */
final class IncomeDataManagerTest {

    @TempDir
    Path tempDir;

    private Path dataFile;
    private IncomeDataManager dataManager;

    /**
     * Initializes a fresh {@link IncomeDataManager} before each test by binding it
     * to a new file within the JUnit-provided temporary directory.
     */
    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestIncome.txt");
        dataManager = new IncomeDataManager(dataFile.toString());
    }

    /**
     * Ensures that {@link IncomeDataManager#formatRecord(Income)} correctly joins
     * fields using the pipe delimiter and sanitizes reserved characters in the
     * description.
     *
     * @throws AddIncomeCommandWrongFormatException if constructing the test income fails
     */
    @Test
    void formatRecord_joinsField_andSanitizesDescription() throws AddIncomeCommandWrongFormatException {
        Income income = new Income("Pipe|Gig%7C", "1234.69");

        String record = dataManager.formatRecord(income);
        assertEquals("Pipe%7CGig%257C|1234.69", record);
    }

    /**
     * Verifies that {@link IncomeDataManager#parseRecord(String)} correctly parses
     * a sanitized record line into an {@link Income} object with unsanitized field
     * values.
     *
     * @throws AddIncomeCommandWrongFormatException if parsing unexpectedly fails
     */
    @Test
    void parseRecord_parseWellFormedLine()
            throws AddIncomeCommandWrongFormatException, AmountPersistCorruptedException {
        String record = "Food%7CTip%257C|12.5";
        Income income = dataManager.parseRecord(record);

        assertNotNull(income);
        assertEquals("Food|Tip%7C", income.getDescription());
        assertEquals(12.5f, income.getAmountEarned(), 1e-6f);
    }

    /**
     * Confirms that {@link IncomeDataManager#parseRecord(String)} returns
     * {@code null} when the input line lacks the required delimiter or number of
     * parts.
     *
     * @throws AddIncomeCommandWrongFormatException if parsing fails for reasons
     *         other than malformed format
     */
    @Test
    void parseRecord_invalidFormat_returnsNull()
            throws AddIncomeCommandWrongFormatException, AmountPersistCorruptedException {
        String record = "Invalid Format";
        Income income = dataManager.parseRecord(record);

        assertNull(income, "Manager should return null if parts less than 2");
    }

    /**
     * Verifies that {@link IncomeDataManager#parseRecord(String)} throws an
     * {@link AmountPersistCorruptedException} when the numeric amount field
     * is invalid.
     */
    @Test
    void parseIncome_throwsException_onNonNumericAmount() {
        String record = "Desc|x.y";
        assertThrows(AmountPersistCorruptedException.class, () -> dataManager.parseRecord(record));
    }

    @Test
    void parseIncome_throwsException_onInvalidIncomeAmount() {
        String record = "Income|0";
        assertThrows(AmountPersistCorruptedException.class, () -> dataManager.parseRecord(record));
    }

    /**
     * Performs a full round-trip test to confirm that written {@link Income}
     * records can be re-loaded without data loss or corruption. Also checks that
     * on-disk content is properly sanitized in UTF-8 encoding.
     *
     * @throws IOException                        if file I/O verification fails
     * @throws AddIncomeCommandWrongFormatException if parsing the reloaded file fails
     * @see IncomeDataManager#writeToFile(List)
     * @see IncomeDataManager#tryLoad()
     */
    @Test
    void writeToFile_tryLoadRoundTrip() throws IOException, AddIncomeCommandWrongFormatException {
        Income income1 = new Income("A|B", "10");     // contains delimiter to test sanitize
        Income income2 = new Income("Monthly Salary", "20");

        dataManager.writeToFile(List.of(income1, income2));

        // Verify on-disk lines (UTF-8, sanitized)
        var lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "A%7CB|10.0",
                "Monthly Salary|20.0"
        ), lines);

        // Load back
        ArrayList<Income> incomes = dataManager.tryLoad();
        assertEquals(2, incomes.size());

        Income first = incomes.get(0);
        assertEquals("A|B", first.getDescription());
        assertEquals(10.0f, first.getAmountEarned(), 1e-6f);

        Income second = incomes.get(1);
        assertEquals("Monthly Salary", second.getDescription());
        assertEquals(20.0f, second.getAmountEarned(), 1e-6f);
    }
}
