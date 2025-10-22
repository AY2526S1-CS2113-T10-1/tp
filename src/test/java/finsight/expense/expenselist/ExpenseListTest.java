package finsight.expense.expenselist;

import finsight.expense.Expense;
import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpenseListTest {
    ExpenseList expenseList;

    @BeforeEach
    void clearList() throws IOException {
        expenseList = new ExpenseList();
        int LoopCount  = Expense.numberOfExpenses;
        for (int i = 0; i < LoopCount; i++) {
            expenseList.deleteExpense(0);
        }
    }
    @Test
    void getExpenses_emptyExpenseList_returnEmptyArrayList() throws IOException {

        assertTrue(expenseList.getExpenses().isEmpty());
    }

    @Test
    void addExpense_singleExpense_returnCorrectArraySize() throws AddExpenseCommandWrongFormatException, IOException {
        
        expenseList.addExpense(new Expense("Food", "10"));

        assertEquals(1, Expense.numberOfExpenses);
    }

    @Test
    void addExpenses_multipleExpenses_returnCorrectArraySize()
            throws AddExpenseCommandWrongFormatException, IOException {
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.addExpense(new Expense("pencils", "50"));

        assertEquals(4, Expense.numberOfExpenses);
    }

    @Test
    void deleteExpenses_oneExpense_returnCorrectArraySize() throws IOException {
        
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.deleteExpense(0);

        assertEquals(2, Expense.numberOfExpenses);
    }

    @Test
    void deleteExpenses_multipleExpenses_returnCorrectArraySize() throws IOException {
        
        expenseList.addExpense(new Expense("Food", "10"));
        expenseList.addExpense(new Expense("Drinks", "20"));
        expenseList.addExpense(new Expense("Snacks", "90"));
        expenseList.addExpense(new Expense("pencils", "50"));
        expenseList.addExpense(new Expense("book", "80"));
        expenseList.deleteExpense(0);
        expenseList.deleteExpense(0);

        assertEquals(3, Expense.numberOfExpenses);
    }

    @Test
    void getExpenses_singleExpense_returnCorrectArrayList() throws IOException {
        
        ArrayList<Expense> expenses = new ArrayList<>();
        Expense object1 = new Expense("Food", "10");
        expenses.add(object1);
        expenseList.addExpense(object1);

        assertEquals(expenses.get(0).getDescription(), expenseList.getExpenses().get(0).getDescription());
    }

    @Test
    void getExpenses_multipleExpense_returnCorrectArrayList() throws IOException {
        
        ArrayList<Expense> expenses = new ArrayList<>();
        Expense object1 = new Expense("Food", "10");
        Expense object2 = new Expense("Drinks", "20");
        expenses.add(object1);
        expenseList.addExpense(object1);
        expenses.add(object2);
        expenseList.addExpense(object2);

        assertEquals(expenses.get(0).getDescription(), expenseList.getExpenses().get(0).getDescription());
        assertEquals(expenses.get(1).getDescription(), expenseList.getExpenses().get(1).getDescription());
    }
}
