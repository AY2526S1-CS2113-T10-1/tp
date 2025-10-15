package finsight.investment.exceptions;

public class DeleteInvestmentIndexOutOfBoundsException extends Exception {
    public String getMessage() {
        return "The index you used for the removal of an investment is not in the list!\n" +
                "Use the <list investment> command to see the indexes of the\n" +
                "investments in the list.";
    }
}
