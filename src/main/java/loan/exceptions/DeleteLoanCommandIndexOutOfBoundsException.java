package loan.exceptions;

/**
 * Exception thrown if delete loan command used with non-existing index
 */
public class DeleteLoanCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Delete Loan index does not exist!!! Please try again with the format:\n" +
                "delete loan <INDEX>\n" +
                "where <INDEX> is a existing loan index shown by the 'list loan' command";
    }
}
