package finsight.expense.expenseList;

import finsight.expense.Expense;
import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import finsight.expense.expenselist.ExpenseList;
import finsight.loan.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class expenseListTest {
    ExpenseList expenseList;

    @BeforeEach
    void clearList() throws IOException {
        for (int i = 0; i < Expense.numberOfExpenses; i++) {
            expenseList.deleteExpense(0);
        }
    }

    @Test
    void getExpenses_emptyExpenseList_returnEmptyArrayList() {
        expenseList = new ExpenseList();
        assertTrue(expenseList.getExpenses().isEmpty());
    }

    @Test
    void addExpense_singleExpense_returnCorrectArraySize() throws AddExpenseCommandWrongFormatException, IOException {
        expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Food", "10"));

        assertEquals(1, Expense.numberOfExpenses);
        expenseList.deleteExpense(0);
    }

    @Test
    void addExpenses_MultipleExpenses_returnCorrectArraySize() throws AddExpenseCommandWrongFormatException, IOException {
        expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.addExpense(new Expense("pencils", "50"));

        assertEquals(4, Expense.numberOfExpenses);
        expenseList.deleteExpense(3);
        expenseList.deleteExpense(2);
        expenseList.deleteExpense(1);
        expenseList.deleteExpense(0);
    }

    @Test
    void deleteExpenses_oneExpense_returnCorrectArraySize() throws IOException {
        expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.deleteExpense(0);

        assertEquals(2, Expense.numberOfExpenses);
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);
    }

    @Test
    void deleteExpenses_multipleExpenses_returnCorrectArraySize() throws IOException {
        expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.addExpense(new Expense("pencils", "50"));
        expenseList.addExpense(new Expense("book", "80"));
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);

        assertEquals(3, Expense.numberOfExpenses);
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);
    }

    @Test
    void getExpenses_singleExpense_returnCorrectArrayList() throws IOException {
        expenseList = new ExpenseList();
        ArrayList<Expense> expenses = new ArrayList<>();
        Expense object1 = new Expense("Food", "10");
        expenses.add(object1);
        expenseList.addExpense(object1);

        assertEquals(expenses.get(0).getDescription(), expenseList.getExpenses().get(0).getDescription());
        expenseList.deleteExpense(0);
    }

    @Test
    void getExpenses_multipleExpense_returnCorrectArrayList() throws IOException {
        expenseList = new ExpenseList();
        ArrayList<Expense> expenses = new ArrayList<>();
        Expense object1 = new Expense("Food", "10");
        Expense object2 = new Expense("Drinks", "20");
        expenses.add(object1);
        expenseList.addExpense(object1);
        expenses.add(object2);
        expenseList.addExpense(object2);

        assertEquals(expenses.get(0).getDescription(), expenseList.getExpenses().get(0).getDescription());
        assertEquals(expenses.get(1).getDescription(), expenseList.getExpenses().get(1).getDescription());
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);
    }
}
