package finsight.investment.exceptions;

public class AddInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Add Investment Command is in the wrong format. Please try again with the format:\n" +
                "add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> r/<RETURN_RATE_PER_ANNUM>\n" +
                "m/<DEPOSIT_DATE_EACH_MONTH>, where <DEPOSIT_DATE_EACH_MONTH> is an integer and\n" +
                "<AMOUNT_INVESTED_MONTHLY> and <RETURN_RATE_PER_ANNUM> and are doubles";
    }
}
