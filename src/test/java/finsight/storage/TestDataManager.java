package finsight.storage;

import java.io.IOException;
import java.nio.file.Path;

final class TestRecord {
    final String testValue;

    TestRecord(String testValue) {
        this.testValue = testValue;
    }
}

public class TestDataManager extends DataManager<TestRecord, Exception> {
    private final Path filePath;

    TestDataManager(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Path dataFilePath() {
        return filePath;
    }

    @Override
    protected TestRecord parseRecord(String line) throws IOException {
        if (line.equals("___PARSE_ERROR___")) {
            throw new IOException("Forced Parse error");
        }
        return new TestRecord(unsanitize(line));
    }

    @Override
    protected String formatRecord(TestRecord record) {
        return sanitize(record.testValue);
    }
}
