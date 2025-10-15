package finsight.investment.exceptions;

public class AddInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Add Investment Command is in the wrong format!!! Please try again with the format:\n" +
                "add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY>, where\n" +
                "<AMOUNT_INVESTED_MONTHLY> is only numbers";
    }
}
