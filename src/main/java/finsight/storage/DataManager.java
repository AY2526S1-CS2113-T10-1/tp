package finsight.storage;

import finsight.ui.Ui;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract data manager that handles reading and writing operations
 * to a text-based storage file. It serves as a base class for managing different
 * types of records such as loans or investments.
 *
 * <p>The {@code DataManager} class provides generic file-handling utilities
 * including ensuring directory and file existence, safe write and append operations,
 * and structured parsing of data lines. Subclasses must define how each record
 * is parsed and formatted by implementing the {@link #parseRecord(String)} and
 * {@link #formatRecord(Object)} methods.</p>
 *
 * <p>This class promotes code reuse and consistency across different storage
 * managers while adhering to the Single Responsibility Principle (SRP)
 * by separating I/O management from domain-specific parsing logic.</p>
 *
 * @author Royden Lim Yi Ren
 *
 * @param <T> the type of record stored and managed (e.g. Loan, Investment)
 * @param <X> the type of exception thrown during parsing operations
 *
 * @since 15 Oct 2025
 */
public abstract class DataManager<T, X extends Exception> {

    /**
     * Returns the path to the data file managed by the subclass.
     * Implementations should specify the location of the file.
     *
     * @return the {@link Path} to the data file
     */
    protected abstract Path dataFilePath();

    /**
     * Converts a line from the data file into a corresponding record object.
     * Implementations define how raw string data is parsed into an object.
     *
     * @param line the line read from the data file
     * @return the parsed record of type {@code T}
     * @throws X if the line cannot be parsed correctly
     */
    protected abstract T parseRecord(String line) throws X;

    /**
     * Converts a record object into its string representation for storage.
     *
     * @param record the record to be written to the file
     * @return a formatted string representation of the record
     */
    protected abstract String formatRecord(T record);

    /**
     * Loads and parses all records from the data file into a list.
     * Automatically skips empty lines and ensures that the file exists.
     *
     * @return a list of parsed records
     * @throws IOException if an I/O error occurs while reading the file
     * @throws X           if parsing any record fails
     */
    private ArrayList<T> load() throws IOException, X {
        ensureFileExist();
        List<String> lines = Files.readAllLines(dataFilePath(), StandardCharsets.UTF_8);
        ArrayList<T> records = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            T record = parseRecord(line);
            if (record != null) {
                records.add(record);
            }
        }
        return records;
    }

    /**
     * Safely loads all records from the data file, handling exceptions gracefully.
     * If an error occurs, it prints the error message to the {@link Ui} and
     * returns an empty list instead of halting the program.
     *
     * @return a list of records, or an empty list if loading fails
     */
    public final ArrayList<T> tryLoad() {
        try {
            return load();
        } catch (Exception e) {
            Ui.printErrorMessage(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Writes the entire list of records to the data file, overwriting existing content.
     * Uses a temporary file and atomic move to ensure data integrity.
     *
     * @param records the list of records to be written
     * @throws IOException if an I/O error occurs during writing
     */
    public void writeToFile(List<T> records) throws IOException {
        assert records != null : "records must not be null";
        ensureFileExist();

        Path tmp = dataFilePath().resolveSibling(dataFilePath().getFileName() + ".temp");
        assert !tmp.equals(dataFilePath()) : "temp path must differ from target path";

        try (BufferedWriter writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (T record : records) {
                assert record != null : "record must not be null";
                writer.write(formatRecord(record));
                writer.newLine();
            }
        }
        Files.move(tmp, dataFilePath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        assert Files.exists(dataFilePath()) : "Target file should exist after move";
    }

    /**
     * Appends a single record to the end of the data file.
     * Ensures the file exists before writing.
     *
     * @param record the record to be appended
     * @throws IOException if an I/O error occurs during appending
     */
    public void appendToFile(T record) throws IOException {
        assert record != null : "record must not be null";
        ensureFileExist();

        try (BufferedWriter writer = Files.newBufferedWriter(dataFilePath(), StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {
            writer.write(formatRecord(record));
            writer.newLine();
        }
    }

    /**
     * Ensures that the parent directory of the data file exists.
     * If it does not exist, it will be created automatically.
     *
     * @throws IOException if directory creation fails
     */
    protected void ensureParentDir() throws IOException {
        Path parent = dataFilePath().getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
            assert Files.isDirectory(parent) : "Parent should be a directory";
        }
    }

    /**
     * Ensures that the data file exists in the file system.
     * Creates the parent directories and an empty file if missing.
     *
     * @throws IOException if file or directory creation fails
     */
    protected void ensureFileExist() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataFilePath())) {
            Files.createFile(dataFilePath());
        }
    }

    /**
     * Sanitizes a given line to remove or escape reserved characters used as delimiters.
     * Useful for preventing parsing issues when writing to file.
     *
     * @param line the raw string to be sanitized
     * @return a sanitized version of the string
     */
    protected String sanitize(String line) {
        return line == null ? "" : line.replace("|", "/");
    }

    /**
     * Restores the original form of a sanitized line.
     * By default, this returns the same string, but subclasses may override
     * to handle custom desanitization logic.
     *
     * @param line the sanitized string
     * @return the restored original string
     */
    protected String unsanitize(String line) {
        return line;
    }
}
