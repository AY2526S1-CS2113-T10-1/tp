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

    @Test
    void toString_multipleCorrectPrintOfDefinedInvestmentObject_isEqual() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Investment test1 = new Investment("test1","100","1");
        Investment test2 = new Investment("test2","50","21");
        Investment test3 = new Investment("test3","20","15");
        Investment test4 = new Investment("test4","2","29");
        assertEquals("Description: test1\nAmount: $100.00\nRecurring Deposit Date of Month: 1", test1.toString());
        assertEquals("Description: test2\nAmount: $50.00\nRecurring Deposit Date of Month: 21", test2.toString());
        assertEquals("Description: test3\nAmount: $20.00\nRecurring Deposit Date of Month: 15", test3.toString());
        assertEquals("Description: test4\nAmount: $2.00\nRecurring Deposit Date of Month: 29", test4.toString());
    }

}
