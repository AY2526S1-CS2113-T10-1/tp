package finsight.loan.exceptions;

/**
 * Exception thrown if loan repaid command used with non-existing index
 */
public class LoanRepaidCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Loan Repaid index is invalid or does not exist. Please try again with the format:\n" +
                "loan repaid <INDEX>\n" +
                "where <INDEX> is an integer and an existing loan index shown by the 'list loan' command";
    }
}
