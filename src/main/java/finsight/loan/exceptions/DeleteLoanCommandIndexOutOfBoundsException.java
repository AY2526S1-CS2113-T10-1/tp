package finsight.loan.exceptions;

/**
 * Exception thrown if delete loan command used with invalid or non-existing index
 */
public class DeleteLoanCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Delete Loan index is invalid or does not exist. Please try again with the format:\n" +
                "\tdelete loan <INDEX>\n" +
                "where <INDEX> is an integer and is an existing loan index shown by the 'list loan' command";
    }
}
