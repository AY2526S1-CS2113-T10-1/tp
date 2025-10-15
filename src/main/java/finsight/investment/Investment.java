package finsight.investment;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;


public class Investment {
    protected String description;
    protected Double investmentAmount;
    protected String investmentType;
    protected int investmentDateOfMonth;

    public Investment(String description, String investmentAmount, String investmentDateOfMonth)
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        this.description = description;
        try {
            this.investmentAmount = Double.parseDouble(investmentAmount);
            int investmentDate = Integer.parseInt(investmentDateOfMonth);
            if (investmentDate < 1 || investmentDate > 31) {
                throw new AddInvestmentDateOutOfBoundsException();
            }
            this.investmentDateOfMonth = investmentDate;
        } catch (NumberFormatException e) {
            throw new AddInvestmentWrongNumberFormatException();
        }
    }

    @Override
    public String toString() {
        String output = "Description: " + description;
        output += "\nAmount: $" + String.format("%.2f", investmentAmount);
        output += "\nRecurring Deposit Date of Month: " + investmentDateOfMonth;
        return output;
    }
}
