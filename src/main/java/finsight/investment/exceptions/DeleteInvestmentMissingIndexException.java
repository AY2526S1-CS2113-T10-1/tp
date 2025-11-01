package finsight.investment.exceptions;

public class DeleteInvestmentMissingIndexException extends Exception {
    public String getMessage() {
        return "Delete Investment Command has missing target index. Please try again with the format:\n" +
                "delete investment <INDEX_TO_DELETE>, where <INDEX_TO_DELETE> is a number within\n" +
                "the span of investments in the current list";
    }
}
