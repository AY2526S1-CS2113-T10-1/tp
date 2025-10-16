package finsight.loan;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

import finsight.loan.exceptions.AddLoanCommandWrongFormatException;

/**
 * Represents a Loan made of a description, a loan amount and a repayment date
 *
 * @author Emannuel Tan Jing Yue
 * @since 2025-10-08
 */
public class Loan {
    public static int numberOfLoans = 0;
    protected static DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    protected static DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("yyyy MMM dd, hh:mm a");
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
     * @throws AddLoanCommandWrongFormatException If amount loaned or loan return date string is not of correct format
     */
    public Loan(String description, String amountLoanedString, String loanReturnDateString)
            throws AddLoanCommandWrongFormatException {
        this.description = description;
        isRepaid = false;

        try {
            this.amountLoaned = Double.parseDouble(amountLoanedString);
            this.loanReturnDate = LocalDateTime.parse(loanReturnDateString, inputDateFormat);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new AddLoanCommandWrongFormatException();
        }
    }

    /**
     * Returns String output of current status and loan amount and return date
     *
     * @return String output of current status and loan amount and return date
     */
    @Override
    public String toString() {
        String outputString = "[";

        outputString += (isRepaid) ? "repaid]" : "outstanding]";

        outputString += "\nDescription: " + description;
        outputString += "\nAmount: $" + String.format("%.2f", amountLoaned);
        outputString += "\nRepayment Deadline: " + loanReturnDate.format(outputDateFormat);

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
