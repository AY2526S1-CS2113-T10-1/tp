package finsight.income.incomelist;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncomeListTest {

    IncomeList incomeList;

    @BeforeEach
    void clearList() throws IOException {
        for (int i = 0; i < Income.numberOfIncomes; i++) {
            incomeList.deleteIncome(0);
        }
    }

    @Test
    void getIncomes_emptyIncomeList_returnEmptyArrayList() {
        incomeList = new IncomeList(new ArrayList<Income>());

        assertTrue(incomeList.getIncomes().isEmpty());
    }

    @Test
    void getIncomes_multipleIncomes_returnArrayList() throws AddIncomeCommandWrongFormatException, IOException {
        ArrayList<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Salary", "1000"));
        incomes.add(new Income("Hustle", "50"));

        incomeList = new IncomeList(incomes);

        assertEquals(incomes, incomeList.getIncomes());
        incomeList.deleteIncome(0);
        incomeList.deleteIncome(0);
    }


    @Test
    void addIncome_singleIncome_returnSize() throws AddIncomeCommandWrongFormatException, IOException {
        incomeList = new IncomeList();

        incomeList.addIncome(new Income("Salary", "1000"));

        assertEquals(1, incomeList.getIncomes().size());
        incomeList.deleteIncome(0);
    }

    @Test
    void deleteIncome_singleIncome_returnSize() throws AddIncomeCommandWrongFormatException, IOException {
        incomeList = new IncomeList();

        incomeList.addIncome(new Income("Salary", "1000"));
        incomeList.addIncome(new Income("Hustle", "50"));

        incomeList.deleteIncome(1);
        assertEquals(1, incomeList.getIncomes().size());
        incomeList.deleteIncome(0);
    }

    @Test
    void editIncome_singleIncome_returnIncomeParameters() throws AddIncomeCommandWrongFormatException, IOException {
        incomeList = new IncomeList();

        incomeList.addIncome(new Income("Salary", "1000"));
        incomeList.editIncome("1", "Hustle", "50");

        assertEquals("Hustle", incomeList.getIncomes().get(0).getDescription());
        assertEquals(50, incomeList.getIncomes().get(0).getAmountEarned());
        incomeList.deleteIncome(0);
    }
}
