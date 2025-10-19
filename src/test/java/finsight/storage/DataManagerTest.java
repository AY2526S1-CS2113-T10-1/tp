package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

final class DataManagerTest {
    @TempDir
    Path tempDir;

    private Path dataFile;
    private TestDataManager testDataManager;

    @BeforeEach
    void setUp() {
        dataFile = tempDir.resolve("TestFile.txt");
        testDataManager = new TestDataManager(dataFile);
    }

    @Test
    void tryLoad_initialFileCreation() {
        var records = testDataManager.tryLoad();
        assertNotNull(records);
        assertTrue(records.isEmpty());
        assertTrue(Files.exists(dataFile));
    }

    @Test
    void appendToFile_appendsAtEnd_andSanitizes() throws IOException {
        testDataManager.writeToFile(List.of(new TestRecord("first")));
        testDataManager.appendToFile(new TestRecord("second"));
        testDataManager.appendToFile(new TestRecord("third|pipe"));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of("first", "second", "third/pipe"), records);
    }

    @Test
    void tryLoad_skipsEmptyLines_andUnsanitizes() throws IOException {
        Files.writeString(dataFile, "\nfirst\n\nsecond/two\n", StandardCharsets.UTF_8);

        var records = testDataManager.tryLoad();
        assertEquals(2, records.size());
        assertEquals("first", records.get(0).testValue);
        assertEquals("second/two", records.get(1).testValue);
    }

    @Test
    void tryLoad_returnsEmpty_onAnyParseError() throws IOException {
        Files.writeString(dataFile, "ok\n___PARSE_ERROR___\n", StandardCharsets.UTF_8);

        var records = testDataManager.tryLoad();
        assertTrue(records.isEmpty(), "Contract: swallow error, print via Ui, return empty");
    }

    @Test
    void writeToFile_createsMissingParentDirectories() throws IOException {
        Path nestedPath = tempDir.resolve("a/b/c/data.txt");
        var nestedTestDataManager = new TestDataManager(nestedPath);

        nestedTestDataManager.writeToFile(List.of(new TestRecord("x")));
        assertTrue(Files.exists(nestedPath));
        assertEquals(List.of("x"), Files.readAllLines(nestedPath, StandardCharsets.UTF_8));
    }
}
