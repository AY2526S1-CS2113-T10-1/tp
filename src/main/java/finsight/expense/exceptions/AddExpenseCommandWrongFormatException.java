package finsight.expense.exceptions;

/**
 * Exception thrown if add expense command has empty fields, incorrect format
 * or incorrect sub commands
 *
 * @author Goh Bin Wee
 * @since 13/Oct/2025
 */
public class AddExpenseCommandWrongFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Add Expense Command is in the wrong format. Please try again with the format:\n" +
                "\tadd expense d/<DESCRIPTION> a/<AMOUNT_SPENT>\n" +
                "where <AMOUNT_SPENT> is only numbers";
    }
}
