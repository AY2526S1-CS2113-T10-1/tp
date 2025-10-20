package finsight.investment.investmentlist;

import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import finsight.investment.Investment;

import java.io.IOException;


public class InvestmentListTest {

    InvestmentList investmentList;

    @BeforeEach
    void clearList() throws IOException {
        for (int i = 0; i < Investment.numberOfInvestments; i++) {
            investmentList.deleteInvestment(0);
        }
    }

    @Test
    void addInvestment_addSingleInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();
        investmentList.addInvestment(new Investment("test1","100","10"));

        assertEquals(1,investmentList.getSize());
        investmentList.deleteInvestment(0);
    }

    @Test
    void addInvestment_addMultipleInvestments_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();

        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));
        investmentList.addInvestment(new Investment("test3","100","10"));

        assertEquals(3,investmentList.getSize());
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
    }

    @Test
    void deleteInvestment_deleteSingleInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();
        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));

        investmentList.deleteInvestment(1);

        assertEquals(1,investmentList.getSize());
        investmentList.deleteInvestment(0);
    }

    @Test
    void deleteInvestment_deleteMultiInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();
        investmentList.addInvestment(new Investment("test1","100","10"));
        investmentList.addInvestment(new Investment("test2","100","10"));
        investmentList.addInvestment(new Investment("test3","100","10"));

        investmentList.deleteInvestment(2);

        assertEquals(2,investmentList.getSize());
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
    }

}
