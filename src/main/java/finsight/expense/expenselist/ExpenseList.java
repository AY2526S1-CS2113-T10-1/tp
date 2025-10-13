package finsight.expense.expenselist;

import finsight.expense.Expense;
import finsight.loan.Loan;
import finsight.ui.Ui;

import java.util.ArrayList;

public class ExpenseList {
    protected ArrayList<Expense> expenses;
    protected Ui ui;

    public ExpenseList(ArrayList<Expense> expenses, Ui ui) {
        this.expenses = expenses;
        this.ui = ui;
    }
    public ExpenseList(Ui ui) {
        this.expenses = new ArrayList<>();
        this.ui = ui;
    }

    public void listExpenses() {
        ui.printAllExpenses(expenses);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        ui.printAddExpenseOutput(expenses);
    }

    public void deleteExpense(int indexToDelete) {
        ui.printDeleteExpenseOutput(expenses, indexToDelete);
        expenses.remove(indexToDelete);
    }
}
