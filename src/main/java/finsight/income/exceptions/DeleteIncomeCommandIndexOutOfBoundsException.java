package finsight.income.exceptions;

/**
 * Exception thrown if delete income command used with non-existing index
 */
public class DeleteIncomeCommandIndexOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "Delete Income index does not exist. Please try again with the format:\n" +
                "delete income <INDEX>\n" +
                "where <INDEX> is a existing income index shown by the 'list income' command";
    }
}
