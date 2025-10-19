package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import finsight.expense.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class ExpenseDataManagerTest {
    @TempDir
    Path tempDir;

    private Path dataFile;
    private ExpenseDataManager dataManager;

    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestExpense.txt");
        dataManager = new ExpenseDataManager(dataFile.toString());
    }

    @Test
    void formatRecord_joinsField_andSanitizesDescription() {
        Expense expense = new Expense("Poop|Food", "1234.69");

        String record = dataManager.formatRecord(expense);
        assertEquals("Poop/Food|1234.69", record);
    }

    @Test
    void parseRecord_parseWellFormedLine() {
        String record = "Poop/Food|1234.69";
        Expense expense = dataManager.parseRecord(record);

        assertNotNull(expense);
        assertEquals("Poop/Food", expense.getDescription());
        assertEquals("1234.69", expense.getExpenseAmount().toString());
    }

    @Test
    void parseRecord_invalidFormat() {
        String record = "Invalid Format";
        Expense expense = dataManager.parseRecord(record);

        assertNull(expense, "Manager should return null if parts less than 2");
    }

    @Test
    void writeToFile_then_tryLoad_roundTripsRecords() throws IOException {
        Expense expense1 = new Expense("Eat|Poop", "10");
        Expense expense2 = new Expense("Poop Poop", "20");

        dataManager.writeToFile(List.of(expense1, expense2));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "Eat/Poop|10.0",
                "Poop Poop|20.0"
        ), records);

        ArrayList<Expense> expenses = dataManager.tryLoad();
        assertEquals(2, expenses.size());

        Expense expenseOne = expenses.get(0);
        assertEquals("Eat/Poop", expenseOne.getDescription());
        assertEquals("10.0", expenseOne.getExpenseAmount().toString());

        Expense expenseTwo = expenses.get(1);
        assertEquals("Poop Poop", expenseTwo.getDescription());
        assertEquals("20.0", expenseTwo.getExpenseAmount().toString());
    }
}
