package finsight.loan.exceptions;

/**
 * Exception thrown if edit loan command is used with negative amount
 */
public class EditLoanCommandInvalidAmountException extends Exception {
    @Override
    public String getMessage() {
        return "Amount Loaned cannot be negative or zero. Please try again with the format:\n" +
                "edit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <LOAN_RETURN_DATE_AND_TIME> is of format 'DD-mm-YYYY HH:mm' and\n" +
                "<AMOUNT_LOANED> is only positive integer or float";
    }
}
