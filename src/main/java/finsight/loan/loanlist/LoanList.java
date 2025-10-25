package finsight.loan.loanlist;

//@@ author Emannuel-Tan

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
    private final LoanDataManager loanDataManager = new LoanDataManager("./data/loan.txt");

    public LoanList(ArrayList<Loan> loans) {
        this.loans = loans;
        Loan.numberOfLoans = loans.size();
    }

    public LoanList() {
        this.loans = loanDataManager.tryLoad();
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
        Ui.printAllLoans(loans);
    }

    /**
     * Adds new Loan
     *
     * @param loan Loan to be added
     */
    public void addLoan(Loan loan) throws IOException {
        loans.add(loan);
        Ui.printAddLoanOutput(loan);

        Loan.numberOfLoans++;
        loanDataManager.appendToFile(loan);
    }

    /**
     * Deletes Loan
     *
     * @param indexToDelete Index of Loan to be deleted
     */
    public void deleteLoan(int indexToDelete) throws IOException {
        Ui.printDeleteLoanOutput(loans, indexToDelete);
        loans.remove(indexToDelete);

        Loan.numberOfLoans--;
        loanDataManager.writeToFile(loans);
    }

    /**
     * Sets loan to be repaid
     *
     * @param indexToSet Index of Loan to be set as repaid
     */
    public void setRepaid(int indexToSet) throws IOException {
        loans.get(indexToSet).setRepaid();
        Ui.printLoanRepaid(loans.get(indexToSet));

        loanDataManager.writeToFile(loans);
    }

    /**
     * Sets loan to be not repaid
     *
     * @param indexToSet Index of Loan to be set as not repaid
     */
    public void setNotRepaid(int indexToSet) throws IOException {
        loans.get(indexToSet).setNotRepaid();
        Ui.printLoanNotRepaid(loans.get(indexToSet));

        loanDataManager.writeToFile(loans);
    }
}
