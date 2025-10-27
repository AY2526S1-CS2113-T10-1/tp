package finsight.loan.loanlist;

//@@author Emannuel-Tan

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;

public class LoanListTest {

    LoanList loanList;

    @BeforeEach
    void clearList() throws IOException {
        loanList = new LoanList();
        int loopCount = Loan.numberOfLoans;
        for (int i = 0; i < loopCount; i++) {
            loanList.deleteLoan(0);
        }
    }

    @Test
    void getLoans_emptyLoanList_returnEmptyArrayList() {
        assertTrue(loanList.getLoans().isEmpty());
    }

    @Test
    void getLoans_multipleLoans_returnCorrectArrayList() throws AddLoanCommandWrongFormatException, IOException {
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("1", "1000", "12-12-2025 19:00"));
        loans.add(new Loan("2", "2000", "12-12-2026 19:00"));

        loanList = new LoanList(loans);
        assertEquals(loans, loanList.getLoans());
    }

    @Test
    void addLoan_addSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        assertEquals(1, loanList.getLoans().size());
    }

    @Test
    void addLoan_addMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));

        assertEquals(3, loanList.getLoans().size());
    }

    @Test
    void deleteLoan_deleteSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(1);

        assertEquals(2, loanList.getLoans().size());
    }

    @Test
    void deleteLoan_deleteMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(2);
        loanList.deleteLoan(1);
        loanList.deleteLoan(0);

        assertEquals(0, loanList.getLoans().size());
    }

    @Test
    void setRepaid_setSingleLoanRepaid_isRepaid() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        loanList.setRepaid(0);
        assertTrue(loanList.getLoans().get(0).isRepaid());
    }

    @Test
    void setNotRepaid_setSingleLoanNotRepaid_isNotRepaid() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        loanList.setRepaid(0);
        loanList.setNotRepaid(0);
        assertFalse(loanList.getLoans().get(0).isRepaid());
    }

    @Test
    void listLoans_singleLoan_noExceptionThrown() throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        assertDoesNotThrow(() -> loanList.listLoans());
    }

    @Test
    void editLoan_singleEdit_editDoneCorrectly() throws AddLoanCommandWrongFormatException, IOException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        String[] inputParameters = new String[4];
        inputParameters[0] = "1";
        inputParameters[1] = "2";
        inputParameters[2] = "2000";
        inputParameters[3] = "12-12-2025 19:02";

        loanList.editLoan(inputParameters);
        assertEquals(inputParameters[1], loanList.getLoans().get(0).getDescription());
        assertEquals(Double.parseDouble(inputParameters[2]), loanList.getLoans().get(0).getAmountLoaned());
        assertEquals(LocalDateTime.parse(inputParameters[3], dateFormat),
                loanList.getLoans().get(0).getLoanReturnDate());
    }
}
