package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Unit tests for the {@link DataManager} abstract class using the
 * {@link TestDataManager} concrete subclass.
 *
 * <p>This test suite verifies the correctness and robustness of core
 * file I/O operations implemented by {@code DataManager}, such as:
 * <ul>
 *   <li>Automatic file and directory creation during first load.</li>
 *   <li>Safe writing and appending of records with sanitization.</li>
 *   <li>Graceful handling of parse errors and empty lines.</li>
 *   <li>Integrity of {@link DataManager#sanitize(String)} and
 *       {@link DataManager#unsanitize(String)} transformations.</li>
 * </ul>
 *
 * <p>Tests are executed on temporary files created under a unique
 * {@link TempDir} for isolation and repeatability. All assertions
 * are made using standard JUnit 5 assertions to ensure contract
 * compliance of the {@code DataManager} API.
 *
 * @author Royden Lim Yi Ren
 * @see DataManager
 * @see TestDataManager
 * @see TestRecord
 */
final class DataManagerTest {

    /** Temporary directory automatically provided by JUnit for isolated test files. */
    @TempDir
    Path tempDir;

    private Path dataFile;
    private TestDataManager testDataManager;

    /**
     * Initializes the test fixture by creating a temporary file path and
     * instantiating a new {@link TestDataManager}.
     */
    @BeforeEach
    void setUp() {
        dataFile = tempDir.resolve("TestFile.txt");
        testDataManager = new TestDataManager(dataFile);
    }

    /**
     * Verifies that {@link DataManager#tryLoad()} automatically creates
     * a new data file on first use and returns an empty record list.
     */
    @Test
    void tryLoad_initialFileCreation() {
        var records = testDataManager.tryLoad();
        assertNotNull(records);
        assertTrue(records.isEmpty());
        assertTrue(Files.exists(dataFile));
    }

    /**
     * Ensures that {@link DataManager#appendToFile(Object)} appends new
     * records at the end of the file and applies sanitization to special
     * characters such as {@code |} and {@code %}.
     *
     * @throws IOException if any I/O error occurs during file operations
     */
    @Test
    void appendToFile_appendsAtEnd_andSanitizes() throws IOException {
        testDataManager.writeToFile(List.of(new TestRecord("first")));
        testDataManager.appendToFile(new TestRecord("second"));
        testDataManager.appendToFile(new TestRecord("third|pipe"));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of("first", "second", "third%7Cpipe"), records);
    }

    /**
     * Verifies that {@link DataManager#tryLoad()} ignores blank lines
     * and correctly unsanitizes stored values when reading from disk.
     *
     * @throws IOException if any I/O error occurs
     */
    @Test
    void tryLoad_skipsEmptyLines_andUnsanitizes() throws IOException {
        Files.writeString(dataFile, "\nfirst|first\n\nsecond%7Ctwo|second\n", StandardCharsets.UTF_8);

        var records = testDataManager.tryLoad();
        assertEquals(2, records.size());
        assertEquals("first|first", records.get(0).testValue);
        assertEquals("second|two|second", records.get(1).testValue);
    }

    /**
     * Confirms that sanitizing and unsanitizing a string round-trips
     * correctly for both percent (%) and pipe (|) characters.
     */
    @Test
    void sanitizeThenUnsanitize_roundTropPercentAndPipe() {
        String record = "Promo 50% | today";
        String stored = testDataManager.sanitize(record);
        String restored = testDataManager.unsanitize(stored);

        assertEquals("Promo 50%25 %7C today", stored);
        assertEquals(record, restored);
    }

    /**
     * Ensures that percent-encoded literals entered by the user are not
     * double-decoded during unsanitization. This test verifies the
     * idempotence and correctness of decoding logic.
     *
     * @throws IOException if file I/O fails
     */
    @Test
    void unsanitize_doesNotDecodeUserTypedPercent() throws IOException {
        Files.writeString(dataFile, "hash=%257Cexact|0\nliteral=%2525token|1\n", StandardCharsets.UTF_8);

        var records = testDataManager.tryLoad();
        assertEquals(2, records.size());
        assertEquals("hash=%7Cexact|0", records.get(0).testValue);
        assertEquals("literal=%25token|1", records.get(1).testValue);
    }

    @Test
    void test() throws IOException {
        Files.writeString(dataFile, "Null\n", StandardCharsets.UTF_8);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        try {
            var records = testDataManager.tryLoad();
            String printed = outputStream.toString();
            assertTrue(records.isEmpty(), "Malformed records should be skipped");
            assertTrue(printed.contains("Skipping malformed record (missing fields) at line 1: Null"));
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Verifies that {@link DataManager#tryLoad()} loads only valid records
     * when encountering partially corrupted data files.
     *
     * <p>Specifically, this test writes two lines into the data file: one valid
     * record ("ok") and one invalid record ("___PARSE_ERROR___"). The test then
     * calls {@code tryLoad()} and asserts that only the valid record is returned,
     * confirming that the method correctly skips over malformed entries instead
     * of aborting the entire load process.</p>
     *
     * @throws IOException if an I/O error occurs while writing to the temporary data file
     */
    @Test
    void tryLoad_returnOnlyValid_onAnyParseError() throws IOException {
        Files.writeString(dataFile, "ok|ok\n___PARSE_ERROR___|error\n", StandardCharsets.UTF_8);

        var records = testDataManager.tryLoad();
        assertEquals(1, records.size());
    }

    /**
     * Ensures that {@link DataManager#writeToFile(List)} creates all
     * necessary parent directories when the target file is nested in
     * a non-existent path.
     *
     * @throws IOException if any I/O error occurs during file creation
     */
    @Test
    void writeToFile_createsMissingParentDirectories() throws IOException {
        Path nestedPath = tempDir.resolve("a/b/c/data.txt");
        var nestedTestDataManager = new TestDataManager(nestedPath);

        nestedTestDataManager.writeToFile(List.of(new TestRecord("x")));
        assertTrue(Files.exists(nestedPath));
        assertEquals(List.of("x"), Files.readAllLines(nestedPath, StandardCharsets.UTF_8));
    }
}
