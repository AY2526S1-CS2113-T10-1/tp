package finsight.income.incomelist;

import finsight.expense.Expense;
import finsight.expense.expenselist.ExpenseList;
import finsight.income.Income;
import finsight.storage.IncomeDataManager;
import finsight.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void editIncome(String indexToEdit, String description, String amountEarned) throws IOException {
        incomes.get(Integer.parseInt(indexToEdit) - 1).setDescription(description);
        incomes.get(Integer.parseInt(indexToEdit) - 1).setAmountEarned(Float.parseFloat(amountEarned));

        Ui.printEditIncomeOutput(incomes, Integer.parseInt(indexToEdit) - 1);
        dataManager.writeToFile(incomes);
    }

    /**
     * Lists all Incomes
     */
    public void listIncomes() {
        if(incomes.isEmpty()) {
            Ui.printEmptyIncome();
            return;
        }
        Ui.printAllIncomes(incomes);
    }

    /**
     * Calculates the total amount of all incomes
     * and expenses
     *
     * Prints total amount of all incomes,
     * total amount of expenses and remaining income
     */
    public void listIncomeOverview(){
        float totalIncome = 0;
        float totalExpense = 0;

        for (Income income : incomes) {
            totalIncome += income.getAmountEarned();
        }

        ExpenseList expenseList = new ExpenseList();

        for (Expense expense : expenseList.getExpenses()){
            totalExpense += expense.getExpenseAmount();
        }

        Ui.printIncomeOverview(totalIncome,totalExpense);
    }
}
