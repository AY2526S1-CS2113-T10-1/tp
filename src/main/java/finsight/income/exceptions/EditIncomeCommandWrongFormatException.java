package finsight.income.exceptions;

public class EditIncomeCommandWrongFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Edit Income is in the wrong format. Please try again with the format:\n" +
                "\tedit income <INDEX> d/ <DESCRIPTION> a/ <AMOUNT_EARNED>\n" +
                "where <INDEX> is a existing income index shown by the 'list income' command";
    }
}
