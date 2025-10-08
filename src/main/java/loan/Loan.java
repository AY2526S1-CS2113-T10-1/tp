package loan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Loan made of a certain loan amount with a repayment date
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
     */
    public Loan(String description, String amountLoanedString, String loanReturnDateString) {
        this.description = description;
        this.amountLoaned = Double.parseDouble(amountLoanedString);
        this.loanReturnDate = LocalDateTime.parse(loanReturnDateString, inputDateFormat);
        isRepaid = false;
    }

    /**
     * Returns String output of current status and loan amount and return date
     *
     * @return String output of current status and loan amount and return date
     */
    public String toString() {
        String outputString = "[";

        if (isRepaid) {
            outputString += "repaid     ";
        } else {
            outputString += "outstanding";
        }

        outputString += "]" +
                "\nDescription: " + description +
                "\nAmount: $" + String.format("%.2f", amountLoaned) +
                "\nRepayment Deadline: " + loanReturnDate.format(outputDateFormat);

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
}
