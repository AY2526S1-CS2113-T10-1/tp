package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class IncomeDataManagerTest {

    @TempDir
    Path tempDir;

    private Path dataFile;
    private IncomeDataManager dataManager;

    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestIncome.txt");
        dataManager = new IncomeDataManager(dataFile.toString());
    }

    @Test
    void formatRecord_joinsField_andSanitizesDescription() throws AddIncomeCommandWrongFormatException {
        Income income = new Income("Pipe|Gig", "1234.69");

        String record = dataManager.formatRecord(income);
        // sanitize should replace '|' with '/'
        assertEquals("Pipe/Gig|1234.69", record);
    }

    @Test
    void parseRecord_parseWellFormedLine() throws AddIncomeCommandWrongFormatException {
        String record = "Food/Tip|12.5";
        Income income = dataManager.parseRecord(record);

        assertNotNull(income);
        assertEquals("Food/Tip", income.getDescription());
        assertEquals(12.5f, income.getAmountEarned(), 1e-6f);
    }

    @Test
    void parseRecord_invalidFormat_returnsNull() throws AddIncomeCommandWrongFormatException {
        String record = "Invalid Format";
        Income income = dataManager.parseRecord(record);

        assertNull(income, "Manager should return null if parts less than 2");
    }

    @Test
    void parseRecord_invalidAmount_throwsException() {
        String record = "Desc|x.y";
        assertThrows(AddIncomeCommandWrongFormatException.class, () -> dataManager.parseRecord(record));
    }

    @Test
    void writeToFile_tryLoadRoundTrip() throws IOException, AddIncomeCommandWrongFormatException {
        Income income1 = new Income("A|B", "10");     // contains delimiter to test sanitize
        Income income2 = new Income("Monthly Salary", "20");

        dataManager.writeToFile(List.of(income1, income2));

        // Verify on-disk lines (UTF-8, sanitized)
        var lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "A/B|10.0",
                "Monthly Salary|20.0"
        ), lines);

        // Load back
        ArrayList<Income> incomes = dataManager.tryLoad();
        assertEquals(2, incomes.size());

        Income first = incomes.get(0);
        assertEquals("A/B", first.getDescription());
        assertEquals(10.0f, first.getAmountEarned(), 1e-6f);

        Income second = incomes.get(1);
        assertEquals("Monthly Salary", second.getDescription());
        assertEquals(20.0f, second.getAmountEarned(), 1e-6f);
    }
}
