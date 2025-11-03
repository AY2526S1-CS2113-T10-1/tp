package finsight.income.exceptions;

/**
 * Exception thrown if delete income command used with non-existing index
 */
public class EditIncomeCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Edit Income index does not exist. Please try again with the format:\n" +
                "\tedit income <INDEX> d/ <DESCRIPTION> a/ <AMOUNT_EARNED>\n" +
                "where <INDEX> is a existing income index shown by the 'list income' command";
    }
}
