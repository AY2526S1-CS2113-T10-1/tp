package finsight.investment.exceptions;

public class AddInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Add Investment Command is in the wrong format. Please try again with the format:\n" +
                "\tadd investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> r/<RETURN_RATE_PER_ANNUM> " +
                "m/<DEPOSIT_DATE_EACH_MONTH> \nwhere <DEPOSIT_DATE_EACH_MONTH> is an integer and " +
                "<AMOUNT_INVESTED_MONTHLY> and <RETURN_RATE_PER_ANNUM> are non-zero doubles";
    }
}
