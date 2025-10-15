package finsight.investment.exceptions;

public class AddInvestmentDateOutOfBoundsException extends Exception {
    public String getMessage() {
        return "Add Investment Command has a recurring deposit date outside the span of dates in a month! Re-enter!";
    }
}
