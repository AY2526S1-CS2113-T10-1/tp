package finsight.expense;

import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;

public class Expense {
    protected String description;
    protected Double expenseAmount;

    public Expense(String description, String expenseAmount) throws AddExpenseCommandWrongFormatException {
        this.description = description;
        try{
            this.expenseAmount = Double.parseDouble(expenseAmount);
        } catch (NumberFormatException e){
            throw new AddExpenseCommandWrongFormatException();
        }
    }

    @Override
    public String toString() {
        StringBuilder outputStringBuilder = new StringBuilder();

        outputStringBuilder.append("\nDescription: ").append(description);
        outputStringBuilder.append("\nAmount: $").append(String.format("%.2f", expenseAmount));

        return outputStringBuilder.toString();
    }
}
