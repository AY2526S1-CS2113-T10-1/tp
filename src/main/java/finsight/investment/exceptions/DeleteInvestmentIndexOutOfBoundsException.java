package finsight.investment.exceptions;

public class DeleteInvestmentIndexOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The index you used for the removal of an investment is not in the list!\n" +
                "Use the <list investments> command to see the indexes of the\n" +
                "investments in the list.\n";
    }
}
