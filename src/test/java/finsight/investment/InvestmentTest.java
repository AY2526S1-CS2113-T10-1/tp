package finsight.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import org.junit.jupiter.api.Test;

public class InvestmentTest {

    @Test
    void constructor_amountContainsAlphabet_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","1hundred","1.0","20"));
    }

    @Test
    void constructor_amountIsZero_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","0","1.0","20"));
    }

    @Test
    void constructor_amountIsNegative_ExceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","-100","1.0","20"));
    }

    @Test
    void constructor_dateSpansOutsideMonth_exceptionThrown() {
        assertThrows(AddInvestmentDateOutOfBoundsException.class,
                () -> new Investment("test","100","1.0","40"));
    }

    @Test
    void constructor_dateContainsAlphabet_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","100","1.0","twenty"));
    }

    @Test
    void constructor_returnRateContainsAlphabet_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","100","onePointOh","20"));
    }

    @Test
    void constructor_returnRateContainsInteger_noExceptionThrown() {
        assertDoesNotThrow(
                () -> new Investment("test","100","1","20"));
    }

    @Test
    void constructor_returnRateIsNegative_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","100","-1.0","20"));
    }

    @Test
    void constructor_returnRateIsZero_noExceptionThrown() {
        assertDoesNotThrow(
                () -> new Investment("test","100","0.0","20"));
    }

    @Test
    void calculateReturnProfits_zeroRate_returnsDeposit()
            throws AddInvestmentDateOutOfBoundsException, AddInvestmentWrongNumberFormatException {
        Investment test = new Investment("test","100","0.0","20");
        assertEquals(100*60,test.calculateReturnProfits(5));
    }

    @Test
    void toString_correctPrintOfDefinedInvestmentObject_isEqual() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Investment test = new Investment("test","100","1.0","20");
        assertEquals("Description: test" +
                        "\nAmount: $100.00" +
                        "\nReturn Rate per Annum: 1.00%" +
                        "\nRecurring Deposit Date of Month: 20" +
                        "\nIn 5 years, you will have gone from: $6000.00 to: $6149.90" +
                        "\nIn 10 years, you will have gone from: $12000.00 to: $12614.99",
                test.toString());
    }

}

