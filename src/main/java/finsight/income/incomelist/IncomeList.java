package finsight.income.incomelist;

import finsight.income.Income;
import finsight.storage.IncomeDataManager;
import finsight.ui.Ui;

import java.io.IOException;
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
    private final IncomeDataManager dataManager = new IncomeDataManager("./data/income.txt");

    public IncomeList(Ui ui) {
        this.incomes = dataManager.tryLoad();
        this.ui = ui;
        Income.numberOfIncomes = incomes.size();
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
     * @params income Income class
     */
    public void addIncome(Income income) throws IOException {
        incomes.add(income);
        ui.printAddIncomeOutput(income);

        Income.numberOfIncomes++;
        dataManager.appendToFile(income);
    }

    /**
     * Deletes Income
     * @params indexToDelete
     */
    public void deleteIncome(int indexToDelete) throws IOException {
        ui.printDeleteIncomeOutput(incomes, indexToDelete);
        incomes.remove(indexToDelete);

        Income.numberOfIncomes--;
        dataManager.writeToFile(incomes);
    }

    /**
     * Edits Income
     * @params indexToEdit
     * @params description
     * @params amountEarned
     */
    public void editIncome(String indexToEdit, String description, String amountEarned){
        incomes.get(Integer.parseInt(indexToEdit) - 1).setDescription(description);
        incomes.get(Integer.parseInt(indexToEdit) - 1).setAmountEarned(Float.parseFloat(amountEarned));

        ui.printEditIncomeOutput(incomes, Integer.parseInt(indexToEdit) - 1);
    }

    public void listIncomes() {
        ui.printAllIncomes(incomes);
    }
}
