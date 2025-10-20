package finsight.expense;

import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpenseTest {
    @Test
    void expense_expenseAmountConsistOfAlphabet_exceptionThrown(){
        assertThrows(AddExpenseCommandWrongFormatException.class,
                ()-> new Expense("Work","onehundred"));
    }

    @Test
    void getDescription_singleExpense_returnsCorrectDescription(){
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Food","10"));

        assertEquals("Food",expenses.get(0).getDescription());

    }

    @Test
    void getDescription_singleExpense_returnsCorrectExpenseAmount(){
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Drinks","50"));

        assertEquals(50,expenses.get(0).getExpenseAmount());

    }
}
