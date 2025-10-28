package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import finsight.loan.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

final class LoanDataManagerTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @TempDir
    Path tempDir;

    private Path dataFile;
    private LoanDataManager dataManager;

    @BeforeEach
    void setUp() {
        dataFile = tempDir.resolve("TestLoan.txt");
        dataManager = new LoanDataManager(dataFile.toString());
    }

    @Test
    void formatRecord_joinsField_andSanitizesDescription() {
        Loan loan = new Loan("Poop|Food", "69.69", "10-10-2025 23:59");
        loan.setRepaid();

        String record = dataManager.formatRecord(loan);
        assertEquals("1|Poop/Food|69.69|10-10-2025 23:59", record);
    }

    @Test
    void formatRecord_handlesNotRepaidFlag() {
        Loan loan = new Loan("PoopieHead", "126.69", "10-10-2025 12:21");

        String record = dataManager.formatRecord(loan);
        assertEquals("0|PoopieHead|126.69|10-10-2025 12:21", record);
    }

    @Test
    void parseRecord_parseWellFormedLine_andSetsRepaidStatus() {
        String record = "1|Eat/Poop|69.126|10-10-2025 23:59";
        Loan loan = dataManager.parseRecord(record);

        assertNotNull(loan);
        assertTrue(loan.isRepaid(), "Repaid flag '1' should set loan as repaid");
        assertEquals("Eat/Poop", loan.getDescription());
        assertEquals("69.126", loan.getAmountLoaned().toString());
        assertEquals("10-10-2025 23:59", loan.getLoanReturnDate().format(FORMATTER));
    }

    @Test
    void parseRecord_parsesNotRepaid_andFields() {
        String record = "0|Buy Poop|200|10-10-2025 00:00";

        Loan loan = dataManager.parseRecord(record);

        assertNotNull(loan);
        assertFalse(loan.isRepaid());
        assertEquals("Buy Poop", loan.getDescription());
        assertEquals("200.0", loan.getAmountLoaned().toString());
        assertEquals("10-10-2025 00:00", loan.getLoanReturnDate().format(FORMATTER));
    }
    @Test
    void parseRecord_returnsNull_whenFieldCountIsWrong() {
        String record = "0|not enough|field";
        Loan loan = dataManager.parseRecord(record);

        assertNull(loan, "Manager should return null for non-4-field lines so caller can skip them");
    }

    @Test
    void writeToFile_tryLoadRoundTrip() throws IOException {
        Loan loan1 = new Loan("Buy|Poop", "69.69", "10-10-2025 23:59");
        Loan loan2 = new Loan("Eat POOP", "200", "10-10-2025 12:21");
        loan2.setRepaid();

        dataManager.writeToFile(List.of(loan1, loan2));

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "0|Buy/Poop|69.69|10-10-2025 23:59",
                "1|Eat POOP|200.0|10-10-2025 12:21"
        ), records);

        var loaded = dataManager.tryLoad();
        assertEquals(2, loaded.size());

        Loan loanOne = loaded.get(0);
        assertFalse(loanOne.isRepaid());
        assertEquals("Buy/Poop",  loanOne.getDescription());
        assertEquals("69.69", loanOne.getAmountLoaned().toString());
        assertEquals("10-10-2025 23:59", loanOne.getLoanReturnDate().format(FORMATTER));

        Loan loanTwo = loaded.get(1);
        assertTrue(loanTwo.isRepaid());
        assertEquals("Eat POOP", loanTwo.getDescription());
        assertEquals("200.0", loanTwo.getAmountLoaned().toString());
        assertEquals("10-10-2025 12:21", loanTwo.getLoanReturnDate().format(FORMATTER));
    }

    @Test
    void appendToFile_appendsInOrder() throws IOException {
        Loan loan1 = new Loan("Poop Poop", "1", "10-10-2025 00:00");
        Loan loan2 = new Loan("NOT ENOUGH POOP", "0",  "10-10-2025 23:59");

        dataManager.writeToFile(List.of(loan1));
        dataManager.appendToFile(loan2);

        var records = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "0|Poop Poop|1.0|10-10-2025 00:00",
                "0|NOT ENOUGH POOP|0.0|10-10-2025 23:59"
        ), records);
    }

    @Test
    void tryLoad_returnsEmpty_onParsingFailure() throws IOException {
        Files.writeString(dataFile,
                "0|BadFormat|60|10-10-2025 23:59\n" +
                "0|Poop Brain|no number|invalid Date", StandardCharsets.UTF_8);

        var records = dataManager.tryLoad();
        assertTrue(records.isEmpty(), "Any parse exception during load should return new List");
    }
}
