package finsight.investment.exceptions;

public class AddInvestmentSubcommandException extends Exception {
    public String getMessage() {
        return "Add Investment Command has missing subcommands!!! Please try again with the format:\n" +
                "add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> m/<DEPOSIT_DATE_EACH_MONTH>, where\n" +
                "<AMOUNT_INVESTED_MONTHLY> and <DEPOSIT_DATE_EACH_MONTH> are only numbers";
    }
}
