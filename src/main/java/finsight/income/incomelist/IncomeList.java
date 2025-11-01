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
    private final Logger logger = Logger.getLogger(IncomeList.class.getName());

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
        logger.log(Level.INFO,"entered add income function");
        incomes.add(income);
        Ui.printAddIncomeOutput(income);

        Income.numberOfIncomes++;
        dataManager.appendToFile(income);
        logger.log(Level.INFO,"income added and added to data file");
    }

    /**
     * Deletes Income
     *
     * @param indexToDelete Index to delete
     */
    public void deleteIncome(int indexToDelete) throws IOException {
        logger.log(Level.INFO,"entered delete income function");
        Ui.printDeleteIncomeOutput(incomes, indexToDelete);
        incomes.remove(indexToDelete);

        Income.numberOfIncomes--;
        dataManager.writeToFile(incomes);
        logger.log(Level.INFO,"income deleted and list updated to data file");
    }

    /**
     * Edits Income
     *
     * @param indexToEdit  Index to delete
     * @param description  Description of income
     * @param amountEarned Amount earned
     */
    public void editIncome(String indexToEdit, String description, String amountEarned) {
        logger.log(Level.INFO,"entered edit income function");
        incomes.get(Integer.parseInt(indexToEdit) - 1).setDescription(description);
        incomes.get(Integer.parseInt(indexToEdit) - 1).setAmountEarned(Float.parseFloat(amountEarned));

        Ui.printEditIncomeOutput(incomes, Integer.parseInt(indexToEdit) - 1);
        logger.log(Level.INFO,"income edited");
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
