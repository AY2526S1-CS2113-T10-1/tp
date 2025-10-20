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
    private final IncomeDataManager dataManager = new IncomeDataManager("./data/income.txt");

    public IncomeList() {
        this.incomes = dataManager.tryLoad();
        Income.numberOfIncomes = incomes.size();
    }

    public IncomeList(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    /**
     * Returns Income ArrayList
     *
     * @return ArrayList of income
     */
    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    /**
     * Adds new Income
     *
     * @param income Income class
     */
    public void addIncome(Income income) throws IOException {
        incomes.add(income);
        Ui.printAddIncomeOutput(income);

        Income.numberOfIncomes++;
        dataManager.appendToFile(income);
    }

    /**
     * Deletes Income
     *
     * @param indexToDelete Index to delete
     */
    public void deleteIncome(int indexToDelete) throws IOException {
        Ui.printDeleteIncomeOutput(incomes, indexToDelete);
        incomes.remove(indexToDelete);

        Income.numberOfIncomes--;
        dataManager.writeToFile(incomes);
    }

    /**
     * Edits Income
     *
     * @param indexToEdit  Index to delete
     * @param description  Description of income
     * @param amountEarned Amount earned
     */
    public void editIncome(String indexToEdit, String description, String amountEarned) {
        incomes.get(Integer.parseInt(indexToEdit) - 1).setDescription(description);
        incomes.get(Integer.parseInt(indexToEdit) - 1).setAmountEarned(Float.parseFloat(amountEarned));

        Ui.printEditIncomeOutput(incomes, Integer.parseInt(indexToEdit) - 1);
    }

    /**
     * Lists all Incomes
     */
    public void listIncomes() {
        Ui.printAllIncomes(incomes);
    }

    /**
     * Lists total amount of all incomes
     */
    public void listIncomeOverview(){
        Ui.printIncomeOverview(incomes);
    }
}
