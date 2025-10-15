package finsight.investment;

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
}

