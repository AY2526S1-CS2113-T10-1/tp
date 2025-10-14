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

    public IncomeList(ArrayList<Income> incomes, Ui ui) {
        this.incomes = incomes;
        this.ui = ui;
    }

    /**
     * Returns Income ArrayList
     *
     * @return ArrayList of income
     */
    public ArrayList<Income> getIncomes(){
        return incomes;
    }

    /**
     * Adds new Income
     */
    public void addIncome(Income income){
        incomes.add(income);
        ui.printAddIncomeOutput(incomes);

        Income.numberOfIncomes++;
    }

    /**
     * Deletes Income
     */
    public void deleteIncome(int indexToDelete) {
        ui.printDeleteIncomeOutput(incomes, indexToDelete);
        incomes.remove(indexToDelete);

        Income.numberOfIncomes--;
    }

    /**
     * Edits Income
     */
    public void editIncome(String indexToEdit, String description, String amountEarned){
        incomes.get(Integer.parseInt(indexToEdit) - 1).setDescription(description);
        incomes.get(Integer.parseInt(indexToEdit) - 1).setAmountEarned(Float.parseFloat(amountEarned));

        ui.printEditIncomeOutput(incomes, Integer.parseInt(indexToEdit) - 1);
    }
}
