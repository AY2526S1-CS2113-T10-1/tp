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
 * Uses the Ui Class to provide input and output to the user via the terminal
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
        this.investmentList = investmentList;
    }

    /**
     * Constructs an InvestmentList Class with a new ArrayList of Investment objects
     */
    public InvestmentList() {
        this.investmentList = dataManager.tryLoad();
    }

    /**
     * Displays all the investments in the list by calling Ui class
     */
    public void listAllInvestments() {
        Ui.printAllInvestments(this.investmentList);
    }

    /**
     * Adds a new investment to the list and displays a confirmation message.
     *
     * @param investment the investment object to be added
     */
    public void addInvestment(Investment investment) throws IOException {
        investmentList.add(investment);
        Ui.printAddInvestmentOutput(this.investmentList);
        dataManager.appendToFile(investment);
    }

    /**
     * Deletes an investment from the list and displays a confirmation message.
     *
     * @param indexToDelete the index of the investment object in the list to delete
     */
    public void deleteInvestment(int indexToDelete) throws IOException {
        Ui.printDeleteInvestmentOutput(this.investmentList, indexToDelete);
        investmentList.remove(indexToDelete);
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


}
