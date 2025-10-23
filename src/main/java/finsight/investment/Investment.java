package finsight.investment;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;

/**
 * Represents a single recurring investment.
 * This class stores details about an investment, such as its description,
 * recurring monthly amount, and annual return rate. It also pre-calculates
 * the projected returns for 5 and 10-year periods upon creation.
 *
 * @author L'kesh Nair
 * @since 15/Oct/2025
 *
 */
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

    /**
     * Constructs a new Investment object from string-based inputs.
     *
     * @param description           The name or description of the investment.
     * @param investmentAmount      String representation of the monthly deposit amount.
     *                              Must be a positive number.
     * @param returnRate            String representation of the annual return rate (e.g., "5.0").
     *                              Must be a non-negative number.
     * @param investmentDateOfMonth String representation of the day of the month (e.g., "15").
     *                              Must be an integer between 1 and 31.
     * @throws AddInvestmentWrongNumberFormatException If investmentAmount or returnRate are not valid numbers.
     *                                                 If investmentAmount is not positive.
     *                                                 If returnRate is negative.
     * @throws AddInvestmentDateOutOfBoundsException   If investmentDateOfMonth is not a valid integer or is not
     *                                                 between 1 and 31.
     */
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
        output += " to: $" + String.format("%.2f", fiveYearReturns);
        output += "\nIn 10 years, you will have gone from: $" + String.format("%.2f", investmentAmount * 120);
        output += " to: $" + String.format("%.2f", tenYearReturns);
        return output;
    }

    /**
     * Calculates the total value of the investment after a specified number of years.
     * This calculation assumes monthly recurring deposits (investmentAmount) and
     * monthly compounding interest based on the annual returnRate.
     *
     * @param totalYears The total number of years to calculate returns for.
     * @return The total projected balance (principal + interest) after the specified period.
     */
    public Double calculateReturnProfits(int totalYears) {
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

    public Double getInvestmentFiveYearReturns() {
        return fiveYearReturns;
    }

    public Double getInvestmentTenYearReturns() {
        return tenYearReturns;
    }
}
