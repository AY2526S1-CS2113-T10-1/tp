package finsight.investment.exceptions;

public class AddInvestmentDateOutOfBoundsException extends Exception {
    public String getMessage() {
        return "Add Investment Command has a recurring deposit date outside the span of dates in a month.\n" +
                "The recurring deposit date should span between 1 to 31, note that months that do not have\n" +
                "the days 29-31 will still display as 30 and 31 respectively\n" +
                "Please re-enter in the following format: add investment d/<DESCRIPTION>\n" +
                "a/<AMOUNT_INVESTED_MONTHLY> r/<RETURN_RATE_PER_ANNUM> m/<DEPOSIT_DATE_EACH_MONTH>, where\n" +
                "<DEPOSIT_DATE_EACH_MONTH> is an integer and <AMOUNT_INVESTED_MONTHLY> and <RETURN_RATE_PER_ANNUM>\n" +
                "are doubles";
    }
}
