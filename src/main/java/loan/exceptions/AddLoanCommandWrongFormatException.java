package loan.exceptions;

/**
 * Exception thrown if add loan command has empty fields or missing sub commands or sub commands in wrong order
 */
public class AddLoanCommandWrongFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Add Loan Command is in the wrong format!!! Please try again with the format:\n" +
                "add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE>\n" +
                "where <LOAN_RETURN_DATE> is of format 'DD-mm-YYYY HH:mm";
    }
}
