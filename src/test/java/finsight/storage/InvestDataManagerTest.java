package finsight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.investment.Investment;
import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class InvestDataManagerTest {

    @TempDir
    Path tempDir;

    private Path dataFile;
    private InvestDataManager dataManager;

    @BeforeEach
    void setup() {
        dataFile = tempDir.resolve("TestInvest.txt");
        dataManager = new InvestDataManager(dataFile.toString());
    }

    @Test
    void formatRecord_joinsFields_andSanitizesDescription()
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        Investment investment = new Investment("Stock|Fund", "1000.50","1.00", "15");

        String record = dataManager.formatRecord(investment);
        assertEquals("Stock/Fund|1000.5|1.0|15", record);
    }

    @Test
    void parseRecord_parsesWellFormedLine()
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        String record = "Crypto/Fund|2000.75|1.0|10";
        Investment investment = dataManager.parseRecord(record);

        assertNotNull(investment);
        assertEquals("Crypto/Fund", investment.getDescription());
        assertEquals(2000.75, investment.getInvestmentAmount());
        assertEquals(10, investment.getInvestmentDateOfMonth());
    }

    @Test
    void parseRecord_invalidFieldCount_returnsNull() throws Exception {
        String malformed = "OnlyTwo|Fields";
        Investment investment = dataManager.parseRecord(malformed);

        assertNull(investment, "Manager should return null when fields < 3");
    }

    @Test
    void parseRecord_invalidNumberFormat_throwsException() {
        String invalidNumber = "Desc|notANumber|1.0|5";
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> dataManager.parseRecord(invalidNumber));
    }

    @Test
    void parseRecord_outOfBoundsDate_throwsException() {
        String invalidDate = "Desc|1000.00|1.0|45";
        assertThrows(AddInvestmentDateOutOfBoundsException.class,
                () -> dataManager.parseRecord(invalidDate));
    }

    @Test
    void writeToFile_tryLoadRoundTrip()
            throws IOException, AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        Investment i1 = new Investment("ETF|Monthly", "500","1.00","5");
        Investment i2 = new Investment("Bonds", "1500.25","1.00","20");

        dataManager.writeToFile(List.of(i1, i2));

        var lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "ETF/Monthly|500.0|1.0|5",
                "Bonds|1500.25|1.0|20"
        ), lines);

        ArrayList<Investment> loaded = dataManager.tryLoad();
        assertEquals(2, loaded.size());

        Investment first = loaded.get(0);
        assertEquals("ETF/Monthly", first.getDescription());
        assertEquals(500.0, first.getInvestmentAmount());
        assertEquals(5, first.getInvestmentDateOfMonth());

        Investment second = loaded.get(1);
        assertEquals("Bonds", second.getDescription());
        assertEquals(1500.25, second.getInvestmentAmount());
        assertEquals(20, second.getInvestmentDateOfMonth());
    }
}
