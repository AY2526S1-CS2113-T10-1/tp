package finsight.income.incomelist;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncomeListTest {

    @Test
    void getIncomes_emptyIncomeList_returnEmptyArrayList(){
        Ui ui = new Ui();
        IncomeList incomeList = new IncomeList(ui);

        assertTrue(incomeList.getIncomes().isEmpty());
    }

    @Test
    void getIncomes_multipleIncomes_returnArrayList() throws AddIncomeCommandWrongFormatException {
        Ui ui = new Ui();

        ArrayList<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Salary","1000"));
        incomes.add(new Income("Hustle","50"));

        IncomeList incomeList = new IncomeList(incomes, ui);

        assertEquals(incomes,incomeList.getIncomes());
    }


    @Test
    void addIncome_singleIncome_returnSize() throws AddIncomeCommandWrongFormatException {
        Ui ui = new Ui();
        IncomeList incomeList = new IncomeList(ui);

        incomeList.addIncome(new Income("Salary","1000"));

        assertEquals(1, incomeList.getIncomes().size());
    }

    @Test
    void deleteIncome_singleIncome_returnSize() throws AddIncomeCommandWrongFormatException {
        Ui ui = new Ui();
        IncomeList incomeList = new IncomeList(ui);

        incomeList.addIncome(new Income("Salary","1000"));
        incomeList.addIncome(new Income("Hustle","50"));

        incomeList.deleteIncome(1);
        assertEquals(1, incomeList.getIncomes().size());
    }

    @Test
    void editIncome_singleIncome_returnIncomeParameters() throws AddIncomeCommandWrongFormatException {
        Ui ui = new Ui();
        IncomeList incomeList = new IncomeList(ui);

        incomeList.addIncome(new Income("Salary","1000"));
        incomeList.editIncome("1","Hustle","50");

        assertEquals("Hustle", incomeList.getIncomes().get(0).getDescription());
        assertEquals(50, incomeList.getIncomes().get(0).getAmountEarned());
    }
}
