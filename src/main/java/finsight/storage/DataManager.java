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

public abstract class DataManager<T, X extends Exception> {
    protected abstract Path dataFilePath();

    protected abstract T parseRecord(String line) throws X;

    protected abstract String formatRecord(T record);

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

    public final ArrayList<T> tryLoad() {
        try {
            return load();
        } catch (Exception e) {
            Ui ui = new Ui();
            ui.printErrorMessage(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void writeToFile(List<T> records) throws IOException {
        ensureFileExist();
        Path tmp = dataFilePath().resolveSibling(dataFilePath().getFileName() + ".temp");
        try  (BufferedWriter writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (T record : records) {
                writer.write(formatRecord(record));
                writer.newLine();
            }
        }
        Files.move(tmp, dataFilePath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public void appendToFile(T record) throws IOException {
        ensureFileExist();
        try  (BufferedWriter writer = Files.newBufferedWriter(dataFilePath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(formatRecord(record));
            writer.newLine();
        }
    }

    protected void ensureParentDir() throws IOException {
        Path parent = dataFilePath().getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    protected void ensureFileExist() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataFilePath())) {
            Files.createFile(dataFilePath());
        }
    }

    protected String sanitize(String line) {
        return line == null? "" : line.replace("|", "/");
    }

    protected String unsanitize(String line) {
        return line;
    }
}
