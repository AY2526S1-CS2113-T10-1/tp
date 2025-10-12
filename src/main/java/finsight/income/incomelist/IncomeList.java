package finsight.income.incomelist;

import finsight.income.Income;
import finsight.ui.Ui;

import java.util.ArrayList;

/**
 * Contains a ArrayList of Income class and manipulate it
 * according to commands given
 *
 * @author Lai Kai Jie Jeremy
 * @since 2025-10-13
 */
public class IncomeList {
    protected ArrayList<Income> incomes;
    protected Ui ui;

    public IncomeList(Ui ui) {
        this.incomes = new ArrayList<>();
        this.ui = ui;
    }

    public void addIncome(Income income){
        incomes.add(income);
        ui.printAddIncomeOutput(incomes);

        Income.numberOfIncomes++;
    }

    public void deleteIncome(int indexToDelete) {
        ui.printDeleteIncomeOutput(incomes, indexToDelete);
        incomes.remove(indexToDelete);

        Income.numberOfIncomes--;
    }
}
