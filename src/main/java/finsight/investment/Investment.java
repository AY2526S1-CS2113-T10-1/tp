package finsight.investment;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;


public class Investment {
    protected String description;
    protected Double investmentAmount;
    protected String investmentType;
    protected int investmentDateOfMonth;

    public Investment(String description, String investmentAmount, String investmentDateOfMonth) throws AddInvestmentWrongNumberFormatException {
        this.description = description;
        try {
            this.investmentAmount = Double.parseDouble(investmentAmount);
            this.investmentDateOfMonth = Integer.parseInt(investmentDateOfMonth);
        } catch (NumberFormatException e) {
            throw new AddInvestmentWrongNumberFormatException();
        }
    }

    @Override
    public String toString() {
        String output = "Investment Description: " + description;
        output += "\nAmount: " + investmentAmount;
        output += "\nRecurring Deposit Date of Month: " + investmentDateOfMonth;
        return output;
    }
}