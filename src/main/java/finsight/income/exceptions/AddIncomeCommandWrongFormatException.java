package finsight.income.exceptions;

/**
 * Exception thrown if add income command has empty fields or missing sub commands or sub commands in wrong order
 */
public class AddIncomeCommandWrongFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Add Income Command is in the wrong format. Please try again with the format:\n" +
                "\tadd income d/<DESCRIPTION> a/<AMOUNT_EARNED>\n" +
                "where <AMOUNT_EARNED> is only numbers";
    }
}
