package finsight.investment.investmentlist;

import finsight.investment.Investment;
import finsight.storage.InvestDataManager;
import finsight.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents an ArrayList of objects of the Investment class
 * This class manipulates the Investment objects in the ArrayList
 * according to the user's command.
 *
 * @author L'kesh Nair
 * @since 15/Oct/2025
 */
public class InvestmentList {
    protected ArrayList<Investment> investmentList;
    private final InvestDataManager dataManager = new InvestDataManager("./data/invest.txt");

    /**
     * Constructs an InvestmentList Class from a pre-assembled ArrayList of Investment objects
     *
     * @param investmentList ArrayList of Investment Objects
     */
    public InvestmentList(ArrayList<Investment> investmentList) {
        assert investmentList != null : "Constructor received a null list.";
        this.investmentList = investmentList;
    }

    /**
     * Constructs an InvestmentList Class with an ArrayList of Investment objects from memory
     */
    public InvestmentList() {
        this.investmentList = dataManager.tryLoad();
        Investment.numberOfInvestments = investmentList.size();
    }

    /**
     * Displays all the investments in the list by calling Ui class
     */
    public void listAllInvestments() {
        if(investmentList.isEmpty()) {
            Ui.printEmptyInvestment();
            return;
        }
        Ui.printAllInvestments(this.investmentList);
        Ui.printInvestmentReturns(String.format("%.2f",getTotal5YearReturns()),
                String.format("%.2f",getTotal10YearReturns()));
    }

    /**
     * Adds a new investment to the list and displays a confirmation message.
     *
     * @param investment the investment object to be added
     */
    public void addInvestment(Investment investment) throws IOException {
        assert investment != null : "Cannot add a null investment.";
        investmentList.add(investment);
        Ui.printAddInvestmentOutput(this.investmentList);
        Investment.numberOfInvestments++;
        dataManager.appendToFile(investment);
    }

    /**
     * Deletes an investment from the list and displays a confirmation message.
     *
     * @param indexToDelete the index of the investment object in the list to delete
     */
    public void deleteInvestment(int indexToDelete) throws IOException {
        assert indexToDelete >= 0 && indexToDelete < investmentList.size() : "Invalid index passed to delete.";
        Ui.printDeleteInvestmentOutput(this.investmentList, indexToDelete);
        investmentList.remove(indexToDelete);
        Investment.numberOfInvestments--;
        dataManager.writeToFile(investmentList);
    }

    /**
     * Returns the total number of Investment objects in the InvestmentList Class
     *
     * @return The total number of Investment objects in the InvestmentList Class
     */
    public int getSize() {
        return investmentList.size();
    }

    /**
     * Returns the overall 5-year returns from all Investment objects in the InvestmentList Class
     *
     * @return The overall 5-year returns from all Investment objects in the InvestmentList Class
     */
    public Double getTotal5YearReturns() {
        Double total5YearReturns = 0.0;
        for (Investment investment : investmentList) {
            total5YearReturns += investment.getInvestmentFiveYearReturns();
        }
        return total5YearReturns;
    }

    /**
     * Returns the overall 10-year returns from all Investment objects in the InvestmentList Class
     *
     * @return The overall 10-year returns from all Investment objects in the InvestmentList Class
     */
    public Double getTotal10YearReturns() {
        Double total10YearReturns = 0.0;
        for (Investment investment : investmentList) {
            total10YearReturns += investment.getInvestmentTenYearReturns();
        }
        return total10YearReturns;
    }


}
