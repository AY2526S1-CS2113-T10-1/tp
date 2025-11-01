package finsight.loan;

// @@author Emannuel-Tan

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Represents a Loan made of a description, a loan amount and a repayment date
 *
 * @author Emannuel Tan Jing Yue
 * @since 2025-10-08
 */
public class Loan {
    public static int numberOfLoans = 0;
    protected static DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    protected static DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    protected String description;
    protected Double amountLoaned;
    protected boolean isRepaid;
    protected LocalDateTime loanReturnDate;

    /**
     * Constructor defaults to not repaid
     *
     * @param description          String description of the loan
     * @param amountLoanedString   String of amount loaned
     * @param loanReturnDateString String of loan return date
     */
    public Loan(String description, String amountLoanedString, String loanReturnDateString) {
        this.description = description;
        isRepaid = false;
        this.amountLoaned = Double.parseDouble(amountLoanedString);
        this.loanReturnDate = LocalDateTime.parse(loanReturnDateString, inputDateFormat);
    }

    /**
     * Returns String output of current status and loan amount and return date
     *
     * @return String output of current status and loan amount and return date
     */
    @Override
    public String toString() {
        String outputString = "[";

        if (isRepaid) {
            outputString += "repaid]";
        } else if (loanReturnDate.isBefore(LocalDateTime.now())) {
            outputString += "OVERDUE]";
        } else {
            outputString += "outstanding]";
        }

        outputString += "\nDescription: " + description;
        outputString += "\nAmount: $" + String.format("%.2f", amountLoaned);
        outputString += "\nRepayment Deadline: " + loanReturnDate.format(outputDateFormat).toUpperCase();

        return outputString;
    }

    /**
     * Returns isRepaid variable
     *
     * @return isRepaid
     */
    public boolean isRepaid() {
        return isRepaid;
    }

    /**
     * Sets isRepaid to true
     */
    public void setRepaid() {
        isRepaid = true;
    }

    /**
     * Sets isRepaid to false
     */
    public void setNotRepaid() {
        isRepaid = false;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmountLoaned() {
        return amountLoaned;
    }

    public LocalDateTime getLoanReturnDate() {
        return loanReturnDate;
    }
}
