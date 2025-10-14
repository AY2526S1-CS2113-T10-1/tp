package finsight.loan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import finsight.loan.exceptions.AddLoanCommandWrongFormatException;

public class LoanTest {
    @Test
    void constructor_amountContainsAlphabet_exceptionThrown() {
        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> new Loan("1", "1hundred", "12-12-2025 19:00"));
    }

    @Test
    void constructor_dateWrongFormat_exceptionThrown() {
        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> new Loan("1", "1000", "12-12-25 19:00"));
    }

    @Test
    void isRepaid_afterConstructor_notRepaid() throws AddLoanCommandWrongFormatException {
        Loan loan = new Loan("1", "1000", "12-12-2025 19:00");

        assertFalse(loan.isRepaid());
    }

    @Test
    void isRepaid_setRepaid_isRepaid() throws AddLoanCommandWrongFormatException {
        Loan loan = new Loan("1", "1000", "12-12-2025 19:00");
        loan.setRepaid();

        assertTrue(loan.isRepaid());
    }

    @Test
    void isRepaid_setNotRepaid_isNotRepaid() throws AddLoanCommandWrongFormatException {
        Loan loan = new Loan("1", "1000", "12-12-2025 19:00");
        loan.setRepaid();
        loan.setNotRepaid();

        assertFalse(loan.isRepaid());
    }
}
