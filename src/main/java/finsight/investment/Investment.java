package finsight.investment;

import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;

public class Investment {
    protected String description;
    protected Double investmentAmount;
    protected String investmentType;
    protected boolean investmentRate;

    public Investment(String description, String investmentAmount) throws AddInvestmentWrongNumberFormatException {
        this.description = description;
        try {
            this.investmentAmount = Double.parseDouble(investmentAmount);
        } catch (NumberFormatException e) {
            throw new AddInvestmentWrongNumberFormatException();
        }
    }
}