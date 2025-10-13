package finsight.loan.loanlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.ui.Ui;

public class LoanListTest {
    @Test
    void getLoans_emptyLoanList_returnEmptyArrayList() {
        Ui ui = new Ui();
        LoanList loanList = new LoanList(ui);

        assertTrue(loanList.getLoans().isEmpty());
    }

    @Test
    void getLoans_multipleLoans_returnCorrectArrayList() throws AddLoanCommandWrongFormatException {
        Ui ui = new Ui();

        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("1", "1000", "12-12-2025 19:00"));
        loans.add(new Loan("2", "2000", "12-12-2026 19:00"));

        LoanList loanList = new LoanList(loans, ui);
        assertEquals(loans, loanList.getLoans());
    }

    @Test
    void addLoan_addSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        assertEquals(1, loanList.getLoans().size());
    }

    @Test
    void addLoan_addMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));

        assertEquals(3, loanList.getLoans().size());
    }

    @Test
    void deleteLoan_deleteSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(1);

        assertEquals(2, loanList.getLoans().size());
    }

    @Test
    void deleteLoan_deleteMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(2);
        loanList.deleteLoan(1);
        loanList.deleteLoan(0);

        assertEquals(0, loanList.getLoans().size());
    }
}
