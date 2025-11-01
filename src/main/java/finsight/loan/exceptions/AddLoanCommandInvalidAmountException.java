package finsight.loan.exceptions;

/**
 * Exception thrown if add loan command is used with zero or negative or lesser than 1 cent amount
 */
public class AddLoanCommandInvalidAmountException extends Exception {
    @Override
    public String getMessage() {
        return "Amount Loaned must be at least 1 cent. Please try again with the format:\n" +
                "add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <LOAN_RETURN_DATE_AND_TIME> is of format 'dd-MM-yyyy HH:mm' and\n" +
                "<AMOUNT_LOANED> is only positive integer or float of at least 1 cent";
    }
}