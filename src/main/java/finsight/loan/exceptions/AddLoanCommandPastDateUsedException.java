package finsight.loan.exceptions;

/**
 * Exception thrown if add loan command is used with past date
 */
public class AddLoanCommandPastDateUsedException extends Exception {
    @Override
    public String getMessage() {
        return "Loan Return Date is in the past. Please try again with the format:\n" +
                "add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <LOAN_RETURN_DATE_AND_TIME> is of format 'DD-mm-YYYY HH:mm' and\n" +
                "<AMOUNT_LOANED> is only integer or float";
    }
}
