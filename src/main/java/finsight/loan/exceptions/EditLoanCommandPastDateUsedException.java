package finsight.loan.exceptions;

/**
 * Exception thrown if edit loan command is used with past date
 */
public class EditLoanCommandPastDateUsedException extends Exception {
    @Override
    public String getMessage() {
        return "Loan Return Date is in the past. Please try again with the format:\n" +
                "\tedit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <LOAN_RETURN_DATE_AND_TIME> is of format 'dd-MM-yyyy HH:mm' and\n" +
                "<AMOUNT_LOANED> is only positive integer or float of at least 1 cent";
    }
}
