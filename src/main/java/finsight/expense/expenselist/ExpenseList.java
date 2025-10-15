package finsight.expense.expenselist;

import finsight.expense.Expense;
import finsight.storage.ExpenseDataManager;
import finsight.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a list of Expense objects and provide methods to manage them
 * This class handles adding, deleting, and listing expenses, while also interacting
 * with the Ui component to display feedback to the user.
 *
 * @author Goh Bin Wee
 * @since 13/Oct/2025
 */
public class ExpenseList {
    protected ArrayList<Expense> expenses;
    protected Ui ui;
    private final ExpenseDataManager dataManager = new ExpenseDataManager("./data/expense.txt");

    public ExpenseList(ArrayList<Expense> expenses, Ui ui) {
        this.expenses = expenses;
        this.ui = ui;
    }
    public ExpenseList(Ui ui) {
        this.expenses = dataManager.tryLoad();
        this.ui = ui;
    }


    /**
     *  Displays all the expenses in the list by calling Ui class
     *
     **/
    public void listExpenses() {
        ui.printAllExpenses(expenses);
    }

    /**
     * Adds a new expense to the list and displays a confirmation message.
     *
     * @param expense the expense object to be added
     */
    public void addExpense(Expense expense) throws IOException {
        expenses.add(expense);
        ui.printAddExpenseOutput(expenses);
        dataManager.appendToFile(expense);
    }

    /**
     * Deletes an expense from the list and displays a confirmation message.
     *
     * @param indexToDelete the index in the ExpenseList to be deleted
     */

    public void deleteExpense(int indexToDelete) throws IOException {
        ui.printDeleteExpenseOutput(expenses, indexToDelete);
        expenses.remove(indexToDelete);
        dataManager.writeToFile(expenses);
    }

    /**
     *  Returns size of arrayList of expenseList
     * @return size of expenseList
     */

    public int getSize() {
        return expenses.size();
    }
}
