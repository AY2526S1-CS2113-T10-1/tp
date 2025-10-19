package finsight.investment.investmentList;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import finsight.investment.Investment;
import finsight.investment.investmentlist.InvestmentList;
import finsight.ui.Ui;


public class InvestmentListTest {
    @Test
    void addInvestment_addSingleInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Ui ui = new Ui();
        InvestmentList investmentList = new InvestmentList(ui);
        investmentList.addInvestment(new Investment("test1","100","10"));

        assertEquals(1,investmentList.getSize());
    }

    @Test
    void addInvestment_addMultipleInvestments_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Ui ui = new Ui();
        InvestmentList investmentList = new InvestmentList(ui);

        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));
        investmentList.addInvestment(new Investment("test3","100","10"));

        assertEquals(3,investmentList.getSize());
    }

    @Test
    void deleteInvestment_deleteSingleInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Ui ui = new Ui();
        InvestmentList investmentList = new InvestmentList(ui);
        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));

        investmentList.deleteInvestment(1);

        assertEquals(1,investmentList.getSize());
    }

    @Test
    void deleteInvestment_deleteMultiInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Ui ui = new Ui();
        InvestmentList investmentList = new InvestmentList(ui);
        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));
        investmentList.addInvestment(new Investment("test3","100","10"));

        investmentList.deleteInvestment(2);

        assertEquals(2,investmentList.getSize());
    }

}
