package finsight.loan.exceptions;

/**
 * Exception thrown if edit loan command used with non-existing index
 */
public class EditLoanCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Edit Loan index does not exist!!! Please try again with the format:\n" +
                "edit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <INDEX> is an existing loan index shown by the 'list loan' command";
    }
}
