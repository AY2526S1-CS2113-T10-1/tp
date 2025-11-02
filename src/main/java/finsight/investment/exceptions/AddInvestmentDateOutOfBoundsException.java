package finsight.investment.exceptions;

public class AddInvestmentDateOutOfBoundsException extends Exception {
    public String getMessage() {
        return "Add Investment Command has a recurring deposit date outside the span of dates in a month.\n" +
                "The recurring deposit date should span between 1 to 31, note that months that do not have the days\n" +
                "29-31 will still display as 30 and 31 respectively\n" +
                "Please re-enter in the following format: add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY>\n" +
                "r/<RETURN_RATE_PER_ANNUM> m/<DEPOSIT_DATE_EACH_MONTH>, where <DEPOSIT_DATE_EACH_MONTH> is an integer\n" +
                "and <AMOUNT_INVESTED_MONTHLY> and <RETURN_RATE_PER_ANNUM> are doubles";
    }
}
