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
        investmentList.addInvestment(new Investment("test1","100","1.00","10"));

        assertEquals(1,investmentList.getSize());
        investmentList.deleteInvestment(0);
    }

    @Test
    void addInvestment_addMultipleInvestments_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();

        investmentList.addInvestment(new Investment("test1","100","1.00","10"));
        investmentList.addInvestment(new Investment("test2","100","1.00","10"));
        investmentList.addInvestment(new Investment("test3","100","1.00","10"));

        assertEquals(3,investmentList.getSize());
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
    }

    @Test
    void deleteInvestment_deleteSingleInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();
        investmentList.addInvestment(new Investment("test1","100","1.00","10"));
        investmentList.addInvestment(new Investment("test2","100","1.00","10"));

        investmentList.deleteInvestment(1);

        assertEquals(1,investmentList.getSize());
        investmentList.deleteInvestment(0);
    }

    @Test
    void deleteInvestment_deleteMultiInvestment_returnCorrectSize() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException, IOException {

        InvestmentList investmentList = new InvestmentList();
        investmentList.addInvestment(new Investment("test1","100","1.00","10"));
        investmentList.addInvestment(new Investment("test2","100","1.00","10"));
        investmentList.addInvestment(new Investment("test3","100","1.00","10"));

        investmentList.deleteInvestment(2);

        assertEquals(2,investmentList.getSize());
        investmentList.deleteInvestment(0);
        investmentList.deleteInvestment(0);
    }

    @Test
    void toString_multipleCorrectPrintOfDefinedInvestmentObject_isEqual() throws AddInvestmentDateOutOfBoundsException,
            AddInvestmentWrongNumberFormatException {
        Investment test1 = new Investment("test1","100","1.00","1");
        Investment test2 = new Investment("test2","50","1.00","21");
        Investment test3 = new Investment("test3","20","1.00","15");
        Investment test4 = new Investment("test4","2","1.00","29");
        assertEquals("Description: test1" +
                "\nAmount: $100.00" +
                "\nReturn Rate per Annum: 1.00%" +
                "\nRecurring Deposit Date of Month: 1" +
                "\nIn 5 years, you will have gone from: $6000.00 to: $6255.03" +
                "\nIn 10 years, you will have gone from: $12000.00 to: $12725.50", test1.toString());
        assertEquals("Description: test2" +
                "\nAmount: $50.00" +
                "\nReturn Rate per Annum: 1.00%" +
                "\nRecurring Deposit Date of Month: 21" +
                "\nIn 5 years, you will have gone from: $3000.00 to: $3127.51" +
                "\nIn 10 years, you will have gone from: $6000.00 to: $6362.75", test2.toString());
        assertEquals("Description: test3" +
                "\nAmount: $20.00" +
                "\nReturn Rate per Annum: 1.00%" +
                "\nRecurring Deposit Date of Month: 15" +
                "\nIn 5 years, you will have gone from: $1200.00 to: $1251.01" +
                "\nIn 10 years, you will have gone from: $2400.00 to: $2545.10", test3.toString());
        assertEquals("Description: test4" +
                "\nAmount: $2.00" +
                "\nReturn Rate per Annum: 1.00%" +
                "\nRecurring Deposit Date of Month: 29" +
                "\nIn 5 years, you will have gone from: $120.00 to: $125.10" +
                "\nIn 10 years, you will have gone from: $240.00 to: $254.51", test4.toString());
    }

}
