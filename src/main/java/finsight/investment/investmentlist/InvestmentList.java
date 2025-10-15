package finsight.investment.investmentlist;

import finsight.investment.Investment;
import finsight.ui.Ui;

import java.util.ArrayList;

public class InvestmentList {
    protected ArrayList<Investment> investmentList;
    protected Ui ui;

    public InvestmentList(ArrayList<Investment> investmentList, Ui ui) {
        this.investmentList = investmentList;
        this.ui = ui;
    }

    public InvestmentList(Ui ui) {
        this.ui = ui;
    }

    public void listAllInvestments() {
        ui.printAllInvestments();
    }

    public void addInvestment(Investment investment) {
        investmentList.add(investment);
        ui.printAddInvestmentOutput();
    }

    public void deleteInvestment(Investment indexToDelete) {
        investmentList.remove(indexToDelete);
        ui.printDeleteInvestmentOutput();
    }

    public int getSize() {
        return investmentList.size();
    }


}
