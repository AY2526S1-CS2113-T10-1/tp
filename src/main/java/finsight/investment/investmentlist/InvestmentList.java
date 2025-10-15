package finsight.investment.investmentlist;

import finsight.investment.Investment;
import finsight.ui.Ui;

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
    protected Ui ui;

    public InvestmentList(ArrayList<Investment> investmentList, Ui ui) {
        this.investmentList = investmentList;
        this.ui = ui;
    }

    public InvestmentList(Ui ui) {
        this.investmentList = new ArrayList<>();
        this.ui = ui;
    }

    public void listAllInvestments() {
        ui.printAllInvestments(this.investmentList);
    }

    public void addInvestment(Investment investment) {
        investmentList.add(investment);
        ui.printAddInvestmentOutput(this.investmentList);
    }

    public void deleteInvestment(int indexToDelete) {
        ui.printDeleteInvestmentOutput(this.investmentList,indexToDelete);
        investmentList.remove(indexToDelete);
    }

    public int getSize() {
        return investmentList.size();
    }


}
