package finsight.investment.exceptions;

public class DeleteInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Delete Investment Command is in the wrong format. Please try again with the format:\n" +
                "delete investment <INDEX_TO_DELETE>, where <INDEX_TO_DELETE> is a number within\n" +
                "the span of investments in the current list, viewable using the <list investment> command";
    }
}
