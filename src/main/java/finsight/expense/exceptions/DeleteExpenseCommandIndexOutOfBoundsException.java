package finsight.expense.exceptions;

public class DeleteExpenseCommandIndexOutOfBoundsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Delete Expense index does not exist!!! Please try again with the format:\n" +
                "delete expense <INDEX>\n" +
                "where <INDEX> is a existing expense index shown by the 'list expense' command";
    }
}
