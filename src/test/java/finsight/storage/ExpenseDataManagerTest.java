package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import finsight.expense.Expense;
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
 * Tests the {@link ExpenseDataManager} persistence behavior and its record
 * formatting/parsing contract for {@link Expense} entities.
 *
 * <p>The suite verifies:
 * <ul>
 *   <li>Field join and delimiter/caret sanitization during {@code formatRecord}.</li>
 *   <li>Correct unsanitization and parsing from a well-formed line.</li>
 *   <li>Graceful handling of invalid record formats (returning {@code null}).</li>
 *   <li>End-to-end round-trip of write → read (I/O) with UTF-8 encoding.</li>
 * </ul>
 * </p>
 *
 * @author Royden Lim Yi Ren
 * @version 1.0
 * @see ExpenseDataManager
 * @see Expense
 * @since 13 Oct 2025
 */
final class ExpenseDataManagerTest {
    @TempDir
    Path tempDir;

    private Path dataFile;
    private ExpenseDataManager dataManager;

    /**
     * Initializes a fresh {@link ExpenseDataManager} pointing to a temporary file
     * before each test.
     */
    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestExpense.txt");
        dataManager = new ExpenseDataManager(dataFile.toString());
    }

    /**
     * Ensures {@link ExpenseDataManager#formatRecord(Expense)} joins fields using the
     * delimiter and sanitizes reserved characters (e.g., {@code |}, {@code %}) in the
     * description.
     */
    @Test
    void formatRecord_joinsField_andSanitizesDescription() {
        Expense expense = new Expense("Poop|Food%", "1234.69");

        String record = dataManager.formatRecord(expense);
        assertEquals("Poop%7CFood%25|1234.69", record);
    }

    /**
     * Verifies that {@link ExpenseDataManager#parseRecord(String)} correctly parses
     * a well-formed, sanitized line into an {@link Expense} with unsanitized fields.
     */
    @Test
    void parseRecord_parseWellFormedLine() throws AmountPersistCorruptedException {
        String record = "Poop%7CFood%25|1234.69";
        Expense expense = dataManager.parseRecord(record);

        assertNotNull(expense);
        assertEquals("Poop|Food%", expense.getDescription());
        assertEquals("1234.69", expense.getExpenseAmount().toString());
    }

    /**
     * Verifies that {@link ExpenseDataManager#parseRecord(String)} returns
     * {@code null} for invalid lines that do not contain the required field
     * delimiter or minimum number of parts.
     */
    @Test
    void parseRecord_invalidFormat() throws AmountPersistCorruptedException {
        String record = "Invalid Format";
        Expense expense = dataManager.parseRecord(record);

        assertNull(expense, "Manager should return null if parts less than 2");
    }

    /**
     * Performs an end-to-end round-trip: writes a list of {@link Expense} objects
     * to disk and reads them back via {@link ExpenseDataManager#tryLoad()}.
     *
     * @throws IOException if the test cannot read the written file for verification
     * @see ExpenseDataManager#writeToFile(List)
     * @see ExpenseDataManager#tryLoad()
     */
    @Test
    void writeToFile_tryLoadRoundTrip() throws IOException {
        Expense expense1 = new Expense("Eat|Poop", "10");
        Expense expense2 = new Expense("Poop Poop", "20");

        dataManager.writeToFile(List.of(expense1, expense2));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "Eat%7CPoop|10.0",
                "Poop Poop|20.0"
        ), records);

        ArrayList<Expense> expenses = dataManager.tryLoad();
        assertEquals(2, expenses.size());

        Expense expenseOne = expenses.get(0);
        assertEquals("Eat|Poop", expenseOne.getDescription());
        assertEquals("10.0", expenseOne.getExpenseAmount().toString());

        Expense expenseTwo = expenses.get(1);
        assertEquals("Poop Poop", expenseTwo.getDescription());
        assertEquals("20.0", expenseTwo.getExpenseAmount().toString());
    }
}
