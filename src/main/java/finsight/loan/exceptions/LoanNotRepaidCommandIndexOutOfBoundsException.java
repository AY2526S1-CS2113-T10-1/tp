package finsight.loan.exceptions;

/**
 * Exception thrown if loan not repaid command used with non-existing index
 */
public class LoanNotRepaidCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Loan Not Repaid index does not exist!!! Please try again with the format:\n" +
                "loan not repaid <INDEX>\n" +
                "where <INDEX> is an existing loan index shown by the 'list loan' command";
    }
}
