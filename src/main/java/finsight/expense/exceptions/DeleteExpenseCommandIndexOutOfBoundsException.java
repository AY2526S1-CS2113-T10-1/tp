package finsight.expense.exceptions;

/**
 * Exception thrown if delete expense command used on out-of-bounds index
 *
 * @author Goh Bin Wee
 * @since 13/Oct/2025
 */
public class DeleteExpenseCommandIndexOutOfBoundsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Delete Expense index does not exist. Please try again with the format:\n" +
                "\tdelete expense <INDEX>\n" +
                "where <INDEX> is a existing expense index shown by the 'list expense' command";
    }
}
