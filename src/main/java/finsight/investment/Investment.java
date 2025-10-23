package finsight.investment;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;


public class Investment {
    public static int numberOfInvestments = 0;
    protected String description;
    protected Double investmentAmount;
    protected Double returnRate;
    protected int investmentDateOfMonth;
    protected Double fiveYearDeposit;
    protected Double tenYearDeposit;
    protected Double fiveYearReturns;
    protected Double tenYearReturns;

    public Investment(String description, String investmentAmount, String returnRate, String investmentDateOfMonth)
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        this.description = description;
        try {
            double parsedInvestAmount = Double.parseDouble(investmentAmount);
            double parsedReturnRate = Double.parseDouble(returnRate);
            int parsedInvestmentDateOfMonth = Integer.parseInt(investmentDateOfMonth);
            if (parsedInvestAmount <= 0 || parsedReturnRate < 0.0) {
                throw new AddInvestmentWrongNumberFormatException();
            }
            if (parsedInvestmentDateOfMonth < 1 || parsedInvestmentDateOfMonth > 31) {
                throw new AddInvestmentDateOutOfBoundsException();
            }
            this.investmentAmount = parsedInvestAmount;
            this.returnRate = parsedReturnRate;
            this.investmentDateOfMonth = parsedInvestmentDateOfMonth;
            this.fiveYearDeposit = this.investmentAmount * 60;
            this.tenYearDeposit = this.investmentAmount * 120;
            this.fiveYearReturns = calculateReturnProfits(5);
            this.tenYearReturns = calculateReturnProfits(10);

            assert this.investmentAmount != null : "investmentAmount is null";
            assert this.returnRate != null : "returnRate is null";
            assert this.fiveYearReturns != null : "fiveYearReturns is null";
            assert this.tenYearReturns != null : "tenYearReturns is null";

            assert this.investmentAmount > 0 : "Investment amount must be positive";
            assert this.returnRate >= 0.0 : "Return rate cannot be negative";
            assert this.investmentDateOfMonth >= 1 && this.investmentDateOfMonth <= 31 : "Date is out of range";

            assert this.tenYearReturns >= this.fiveYearReturns : "10-year returns should be >= 5-year returns";
            assert this.fiveYearReturns >= this.fiveYearDeposit : "5-year returns should be >= 5-year deposit";

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
        output += " to: $" + String.format("%.2f",fiveYearReturns);
        output += "\nIn 10 years, you will have gone from: $" + String.format("%.2f", investmentAmount * 120);
        output += " to: $" + String.format("%.2f",tenYearReturns);
        return output;
    }

    public Double calculateReturnProfits(int totalYears) {
        assert totalYears > 0 : "totalYears must be positive. Received: " + totalYears;
        assert this.investmentAmount != null && this.investmentAmount > 0 :
                "Investment amount not initialized";
        assert this.returnRate != null && this.returnRate >= 0.0 : "Return rate not initialized";

        int totalMonths = totalYears * 12;
        double monthlyReturnRate = (returnRate / 100.0) / 12.0;

        double totalBalance = 0.0;

        for (int month = 0; month < totalMonths; month++) {
            double interestThisMonth = totalBalance * monthlyReturnRate;
            totalBalance += interestThisMonth;
            totalBalance += investmentAmount;
        }
        return totalBalance;
    }

    public String getDescription() {
        assert description != null : "Invariant failed: description is null";
        return description;
    }

    public Double getInvestmentAmount() {
        assert investmentAmount != null && investmentAmount > 0 : "Invariant failed: investmentAmount is invalid";
        return investmentAmount;
    }

    public int getInvestmentDateOfMonth() {
        assert investmentDateOfMonth >= 1 && investmentDateOfMonth <= 31 : "Invariant failed: date is invalid";
        return investmentDateOfMonth;
    }

    public Double getInvestmentReturnRate() {
        assert returnRate != null && returnRate > 0: "Invariant failed:return rate is invalid";
        return returnRate;
    }

    public Double getInvestmentFiveYearReturns() {
        assert fiveYearReturns != null : "Invariant failed: 5-year return is null";
        return fiveYearReturns;
    }

    public Double getInvestmentTenYearReturns() {
        assert tenYearReturns != null : "Invariant failed: 5-year return is null";
        return tenYearReturns;
    }
}
