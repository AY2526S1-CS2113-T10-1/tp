package finsight.investment.exceptions;

public class DeleteInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Delete Investment Command is in the wrong format. Please try again with the format:\n" +
                "\tdelete investment <INDEX_TO_DELETE> \nwhere <INDEX_TO_DELETE> is a number within " +
                "the span of investments in the current list, viewable using the <list investment> command";
    }
}
