package finsight.investment.exceptions;

public class DeleteInvestmentWrongNumberFormatException extends Exception {
    public String getMessage() {
        return "Delete Investment Command is in the wrong format!!! Please try again with the format:\n" +
                "delete investment <INDEX>";
    }
}
