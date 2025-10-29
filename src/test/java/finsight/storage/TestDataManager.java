package finsight.storage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a simple test record used for verifying the behavior of
 * {@link DataManager} during unit testing.
 *
 * <p>This class wraps a single string value that simulates the data
 * content of a record stored in a text file. It is intentionally minimal
 * to isolate storage-related logic from domain-specific behavior.
 *
 * @author Royden Lim Yi Ren
 * @see TestDataManager
 */
final class TestRecord {
    final String testValue;

    TestRecord(String testValue) {
        this.testValue = testValue;
    }
}

/**
 * A concrete subclass of {@link DataManager} used exclusively for testing.
 *
 * <p>{@code TestDataManager} provides a lightweight implementation of the
 * abstract methods defined in {@code DataManager} to facilitate unit tests
 * without depending on domain-specific classes (such as {@code Expense} or
 * {@code Investment}). It verifies that file I/O, sanitization, and error
 * handling in the {@code DataManager} base class work as intended.
 *
 * <p>Each record is represented by a {@link TestRecord} object and is
 * serialized to a plain text line. The manager reads from and writes to a
 * test file path provided at construction.
 *
 * <h2>Behavior</h2>
 * <ul>
 *   <li>Throws an {@link IOException} when encountering the special line
 *       value {@code "___PARSE_ERROR___"} to simulate parse failures.</li>
 *   <li>Uses {@link DataManager#sanitize(String)} and
 *       {@link DataManager#unsanitize(String)} to verify correctness of
 *       delimiter escaping and restoration.</li>
 * </ul>
 *
 * @author Your Name
 * @see DataManager
 */
final class TestDataManager extends DataManager<TestRecord, Exception> {
    private final Path filePath;

    /**
     * Constructs a {@code TestDataManager} for the specified file path.
     *
     * @param filePath the path to the test file where records are read and written
     */
    TestDataManager(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the path to the test data file.
     *
     * @return the path to the file managed by this instance
     */
    @Override
    protected Path dataFilePath() {
        return filePath;
    }

    /**
     * Parses a single line from the data file into a {@link TestRecord}.
     *
     * <p>If the line equals the special marker {@code "___PARSE_ERROR___"},
     * this method throws an {@link IOException} to simulate a read error
     * during unit testing.</p>
     *
     * @param line the raw line read from the data file
     * @return a {@code TestRecord} containing the unsanitized value
     * @throws IOException if the line equals {@code "___PARSE_ERROR___"}
     */
    @Override
    protected TestRecord parseRecord(String line) throws IOException {
        if (line.equals("___PARSE_ERROR___")) {
            throw new IOException("Forced Parse error");
        }
        return new TestRecord(unsanitize(line));
    }

    /**
     * Converts the given {@link TestRecord} into its sanitized string form
     * for writing to the data file.
     *
     * @param record the {@code TestRecord} to format
     * @return the sanitized string representation of the record
     */
    @Override
    protected String formatRecord(TestRecord record) {
        return sanitize(record.testValue);
    }
}
