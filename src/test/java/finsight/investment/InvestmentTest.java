package finsight.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import org.junit.jupiter.api.Test;

public class InvestmentTest {

    @Test
    void constructor_amountContainsAlphabet_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","1hundred","20"));
    }

    @Test
    void constructor_dateSpansOutsideMonth_exceptionThrown() {
        assertThrows(AddInvestmentDateOutOfBoundsException.class,
                () -> new Investment("test","100","40"));
    }

    @Test
    void constructor_dateContainsAlphabet_exceptionThrown() {
        assertThrows(AddInvestmentWrongNumberFormatException.class,
                () -> new Investment("test","100","twenty"));
    }

    @Test
    void toString_correctPrintOfDefinedInvestmentObject_isEqual() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Investment test = new Investment("test","100","20");
        assertEquals("Description: test\nAmount: $100.00\nRecurring Deposit Date of Month: 20", test.toString());
    }

}

