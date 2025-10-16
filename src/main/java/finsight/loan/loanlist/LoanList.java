package finsight.loan.loanlist;

import java.io.IOException;
import java.util.ArrayList;

import finsight.loan.Loan;
import finsight.storage.LoanDataManager;
import finsight.ui.Ui;

/**
 * Contains a ArrayList of Loan class and manipulate it
 * according to commands given
 *
 * @author Emannuel Tan Jing Yue
 * @since 2025-09-21
 */
public class LoanList {
    protected ArrayList<Loan> loans;
    protected Ui ui;
    private final LoanDataManager loanDataManager = new LoanDataManager("./data/loan.txt");

    public LoanList(ArrayList<Loan> loans, Ui ui) {
        this.loans = loans;
        this.ui = ui;
    }

    public LoanList(Ui ui) {
        this.loans = loanDataManager.tryLoad();
        this.ui = ui;
        Loan.numberOfLoans = loans.size();
    }

    /**
     * Returns ArrayList of loans
     *
     * @return ArrayList of loans
     */
    public ArrayList<Loan> getLoans() {
        return loans;
    }

    /**
     * Calls the Ui class to print all tasks
     */
    public void listLoans() {
        ui.printAllLoans(loans);
    }

    /**
     * Adds new Loan
     *
     * @param loan Loan to be added
     */
    public void addLoan(Loan loan) throws IOException {
        loans.add(loan);
        ui.printAddLoanOutput(loan);

        Loan.numberOfLoans++;
        loanDataManager.appendToFile(loan);
    }

    /**
     * Deletes Loan
     *
     * @param indexToDelete Index of Loan to be deleted
     */
    public void deleteLoan(int indexToDelete) throws IOException {
        ui.printDeleteLoanOutput(loans, indexToDelete);
        loans.remove(indexToDelete);

        Loan.numberOfLoans--;
        loanDataManager.writeToFile(loans);
    }

    /**
     * Sets loan to be repaid
     *
     * @param indexToSet Index of Loan to be set as repaid
     */
    public void setRepaid(int indexToSet) {
        loans.get(indexToSet).setRepaid();
        ui.printLoanRepaid(loans.get(indexToSet));
    }

    /**
     * Sets loan to be not repaid
     *
     * @param indexToSet Index of Loan to be set as not repaid
     */
    public void setNotRepaid(int indexToSet) {
        loans.get(indexToSet).setNotRepaid();
        ui.printLoanNotRepaid(loans.get(indexToSet));
    }
}
