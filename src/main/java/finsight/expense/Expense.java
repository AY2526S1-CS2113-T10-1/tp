package finsight.expense;

public class Expense {
    protected String description;
    protected Double expenseAmount;

    public Expense(String description, Double expenseAmount) {
        this.description = description;
        this.expenseAmount = expenseAmount;
    }

    @Override
    public String toString() {
        StringBuilder outputStringBuilder = new StringBuilder();

        outputStringBuilder.append("\nDescription: ").append(description);
        outputStringBuilder.append("\nAmount: $").append(String.format("%.2f", expenseAmount));

        return outputStringBuilder.toString();
    }
}
