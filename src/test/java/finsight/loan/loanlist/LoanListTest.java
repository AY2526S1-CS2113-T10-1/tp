package finsight.loan.loanlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.ui.Ui;

public class LoanListTest {

    LoanList loanList;

    @BeforeEach
    void clearList() throws IOException {
        for (int i = 0; i < Loan.numberOfLoans; i++) {
            loanList.deleteLoan(0);
        }
    }

    @Test
    void getLoans_emptyLoanList_returnEmptyArrayList() {
        Ui ui = new Ui();
        loanList = new LoanList(ui);
        System.out.println(loanList.getLoans());
        assertTrue(loanList.getLoans().isEmpty());
    }

    @Test
        void getLoans_multipleLoans_returnCorrectArrayList() throws AddLoanCommandWrongFormatException, IOException {
        Ui ui = new Ui();

        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("1", "1000", "12-12-2025 19:00"));
        loans.add(new Loan("2", "2000", "12-12-2026 19:00"));

        loanList = new LoanList(loans, ui);
        assertEquals(loans, loanList.getLoans());
        loanList.deleteLoan(0);
        loanList.deleteLoan(0);
    }

    @Test
    void addLoan_addSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        Ui ui = new Ui();

        loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));

        assertEquals(1, loanList.getLoans().size());
        loanList.deleteLoan(0);
    }

    @Test
    void addLoan_addMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        Ui ui = new Ui();

        loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));

        assertEquals(3, loanList.getLoans().size());
        loanList.deleteLoan(0);
        loanList.deleteLoan(0);
        loanList.deleteLoan(0);
    }

    @Test
    void deleteLoan_deleteSingleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        Ui ui = new Ui();

        loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(1);

        assertEquals(2, loanList.getLoans().size());
        loanList.deleteLoan(0);
        loanList.deleteLoan(0);
    }

    @Test
    void deleteLoan_deleteMultipleLoan_returnCorrectSize() throws AddLoanCommandWrongFormatException, IOException {
        Ui ui = new Ui();

        loanList = new LoanList(ui);
        loanList.addLoan(new Loan("1", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("2", "1000", "12-12-2025 19:00"));
        loanList.addLoan(new Loan("3", "1000", "12-12-2025 19:00"));
        loanList.deleteLoan(2);
        loanList.deleteLoan(1);
        loanList.deleteLoan(0);

        assertEquals(0, loanList.getLoans().size());
    }
}
