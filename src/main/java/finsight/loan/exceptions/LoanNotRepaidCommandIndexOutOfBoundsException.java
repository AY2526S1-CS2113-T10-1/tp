package finsight.loan.exceptions;

/**
 * Exception thrown if loan not repaid command used with invalid or non-existing index
 */
public class LoanNotRepaidCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Loan Not Repaid index is invalid or does not exist. Please try again with the format:\n" +
                "\tloan not repaid <INDEX>\n" +
                "where <INDEX> is an integer and an existing loan index shown by the 'list loan' command";
    }
}
