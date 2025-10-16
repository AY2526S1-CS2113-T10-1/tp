package finsight.expense;

import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;

/**
 * Represents an expense object with the item/description and the amount used
 *
 * @author Goh Bin Wee
 * @since 13/Oct/2025
 */
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

    public String getDescription() {
        return description;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    @Override
    public String toString() {
        StringBuilder outputStringBuilder = new StringBuilder();

        outputStringBuilder.append("\nDescription: ").append(description);
        outputStringBuilder.append("\nAmount: $").append(String.format("%.2f", expenseAmount));

        return outputStringBuilder.toString();
    }
}
