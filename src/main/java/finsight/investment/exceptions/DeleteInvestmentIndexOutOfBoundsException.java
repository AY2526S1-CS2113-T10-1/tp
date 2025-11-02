package finsight.investment.exceptions;

public class DeleteInvestmentIndexOutOfBoundsException extends Exception {
    public String getMessage() {
        return "The index you used for the removal of an investment is not in the list.\n" +
                "Use the <list investment> command to see the indexes of the\n" +
                "investments in the list.\n" +
                "Please try again with the format: delete investment <INDEX_TO_DELETE>, where <INDEX_TO_DELETE> is\n" +
                "a number within the span of investments in the current list";
    }
}
