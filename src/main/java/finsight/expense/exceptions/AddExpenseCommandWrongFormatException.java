package finsight.expense.exceptions;

public class AddExpenseCommandWrongFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Add Expense Command is in the wrong format. Please try again with the format:\n" +
                "add loan d/<DESCRIPTION> a/<EXPENDED_AMOUNT>\n" +
                "where <EXPENDED_AMOUNT> is only numbers" ;
    }
}
