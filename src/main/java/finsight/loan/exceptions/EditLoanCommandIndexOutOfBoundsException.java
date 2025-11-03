package finsight.loan.exceptions;

/**
 * Exception thrown if edit loan command used with invalid or non-existing index
 */
public class EditLoanCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Edit Loan index is invalid or does not exist. Please try again with the format:\n" +
                "\tedit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>\n" +
                "where <INDEX> is an integer and an existing loan index shown by the 'list loan' command";
    }
}
