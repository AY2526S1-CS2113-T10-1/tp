package finsight.investment;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;


public class Investment {
    public static int numberOfInvestments = 0;
    protected String description;
    protected Double investmentAmount;
    protected Double returnRate;
    protected int investmentDateOfMonth;

    public Investment(String description, String investmentAmount, String returnRate, String investmentDateOfMonth)
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        this.description = description;
        try {
            this.investmentAmount = Double.parseDouble(investmentAmount);
            this.returnRate = Double.parseDouble(returnRate);
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
        output += "\nReturn Rate per Annum: " + String.format("%.2f", returnRate) + "%";
        output += "\nRecurring Deposit Date of Month: " + investmentDateOfMonth;
        output += "\nIn 5 years, you will have gone from: $" + String.format("%.2f", investmentAmount * 60);
        output += " to: $" + String.format("%.2f",calculateReturnProfits(5));
        output += "\nIn 10 years, you will have gone from: $" + String.format("%.2f", investmentAmount * 120);
        output += " to: $" + String.format("%.2f",calculateReturnProfits(10));
        return output;
    }

    private Double calculateReturnProfits(int totalYears) {
        int totalMonths = totalYears * 12;
        double monthlyReturnRate = (returnRate / 100.0) / 12.0;

        double totalBalance = investmentAmount;

        for (int month = 0; month < totalMonths; month++) {
            double interestThisMonth = totalBalance * monthlyReturnRate;
            totalBalance += interestThisMonth;
            totalBalance += investmentAmount;
        }
        return totalBalance;
    }

    public String getDescription() {
        return description;
    }

    public Double getInvestmentAmount() {
        return investmentAmount;
    }

    public int getInvestmentDateOfMonth() {
        return investmentDateOfMonth;
    }

    public Double getInvestmentReturnRate() {
        return returnRate;
    }
}
