package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.investment.Investment;
import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import finsight.storage.exceptions.AmountPersistCorruptedException;
import finsight.storage.exceptions.DayOfInvestPersistCorruptedException;
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
 * Tests {@link InvestDataManager} for correct serialization, sanitization,
 * parsing, and end-to-end persistence of {@link Investment} records.
 *
 * <p>The suite covers:
 * <ul>
 *   <li>Field joining and delimiter sanitization in {@code formatRecord}.</li>
 *   <li>Parsing of well-formed lines and null on malformed field counts.</li>
 *   <li>Exception signaling for invalid numeric formats and out-of-bounds dates.</li>
 *   <li>Write → read round-trip with UTF-8 on-disk verification.</li>
 * </ul>
 * </p>
 *
 * @author Royden Lim Yi Ren
 * @version 1.0
 * @see InvestDataManager
 * @see Investment
 * @since 13 Oct 2025
 */
final class InvestDataManagerTest {

    @TempDir
    Path tempDir;

    private Path dataFile;
    private InvestDataManager dataManager;

    /**
     * Prepares a fresh {@link InvestDataManager} instance targeting a new file
     * within the JUnit-provided temporary directory.
     */
    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestInvest.txt");
        dataManager = new InvestDataManager(dataFile.toString());
    }

    /**
     * Ensures {@link InvestDataManager#formatRecord(Investment)} joins fields
     * with the pipe delimiter and sanitizes reserved characters in description.
     *
     * @throws AddInvestmentWrongNumberFormatException if constructing test input fails
     * @throws AddInvestmentDateOutOfBoundsException   if constructing test input fails
     */
    @Test
    void formatRecord_joinsFields_andSanitizesDescription()
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        Investment investment = new Investment("Stock|Fund", "1000.50","1.00", "15");

        String record = dataManager.formatRecord(investment);
        assertEquals("Stock%7CFund|1000.5|1.0|15", record);
    }

    /**
     * Verifies that {@link InvestDataManager#parseRecord(String)} parses a
     * well-formed, sanitized line into an {@link Investment} with correct values.
     *
     * @throws AddInvestmentWrongNumberFormatException if parsing fails on numbers
     * @throws AddInvestmentDateOutOfBoundsException   if parsing fails on date bounds
     */
    @Test
    void parseRecord_parsesWellFormedLine()
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException,
            AmountPersistCorruptedException, DayOfInvestPersistCorruptedException {
        String record = "Crypto%7CFund|2000.75|1.0|10";
        Investment investment = dataManager.parseRecord(record);

        assertNotNull(investment);
        assertEquals("Crypto|Fund", investment.getDescription());
        assertEquals(2000.75, investment.getInvestmentAmount());
        assertEquals(10, investment.getInvestmentDateOfMonth());
    }

    /**
     * Confirms that {@link InvestDataManager#parseRecord(String)} returns
     * {@code null} when the field count is insufficient.
     *
     * @throws Exception not expected, but declared to mirror test signature
     */
    @Test
    void parseRecord_invalidFieldCount_returnsNull() throws Exception {
        String malformed = "OnlyTwo|Fields";
        Investment investment = dataManager.parseRecord(malformed);

        assertNull(investment, "Manager should return null when fields < 3");
    }

    /**
     * Ensures {@link InvestDataManager#parseRecord(String)} throws an
     * {@link AmountPersistCorruptedException} if numeric fields are invalid.
     */
    @Test
    void parseRecord_invalidNumberFormat_throwsException() {
        String invalidNumber = "Desc|notANumber|1.0|5";
        assertThrows(AmountPersistCorruptedException.class,
                () -> dataManager.parseRecord(invalidNumber));
    }

    /**
     * Ensures {@link InvestDataManager#parseRecord(String)} throws an
     * {@link AddInvestmentDateOutOfBoundsException} if the day-of-month is out of bounds.
     */
    @Test
    void parseRecord_outOfBoundsDate_throwsException() {
        String invalidDate = "Desc|1000.00|1.0|45";
        assertThrows(AddInvestmentDateOutOfBoundsException.class,
                () -> dataManager.parseRecord(invalidDate));
    }

    /**
     * Performs a write → read round-trip to verify that serialized {@link Investment}
     * records are persisted in UTF-8 and deserialized without loss or corruption.
     *
     * @throws IOException                             if on-disk verification fails
     * @throws AddInvestmentWrongNumberFormatException if re-parsing fails on numbers
     * @throws AddInvestmentDateOutOfBoundsException   if re-parsing fails on date bounds
     * @see InvestDataManager#writeToFile(List)
     * @see InvestDataManager#tryLoad()
     */
    @Test
    void writeToFile_tryLoadRoundTrip()
            throws IOException, AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        Investment i1 = new Investment("ETF|Monthly", "500","1.00","5");
        Investment i2 = new Investment("Bonds", "1500.25","1.00","20");

        dataManager.writeToFile(List.of(i1, i2));

        var lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "ETF%7CMonthly|500.0|1.0|5",
                "Bonds|1500.25|1.0|20"
        ), lines);

        ArrayList<Investment> loaded = dataManager.tryLoad();
        assertEquals(2, loaded.size());

        Investment first = loaded.get(0);
        assertEquals("ETF|Monthly", first.getDescription());
        assertEquals(500.0, first.getInvestmentAmount());
        assertEquals(5, first.getInvestmentDateOfMonth());

        Investment second = loaded.get(1);
        assertEquals("Bonds", second.getDescription());
        assertEquals(1500.25, second.getInvestmentAmount());
        assertEquals(20, second.getInvestmentDateOfMonth());
    }
}
