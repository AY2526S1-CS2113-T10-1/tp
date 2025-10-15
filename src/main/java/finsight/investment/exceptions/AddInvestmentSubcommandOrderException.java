package finsight.investment.exceptions;

public class AddInvestmentSubcommandOrderException extends Exception {
    public String getMessage() {
        return "Add Investment Command is using the wrong format!!! Please try again with this format:\n" +
                "add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> m/<DEPOSIT_DATE_EACH_MONTH>, where\n" +
                "<AMOUNT_INVESTED_MONTHLY> and <DEPOSIT_DATE_EACH_MONTH> are only numbers";
    }
}
