package finsight.income;

import finsight.income.exceptions.AddIncomeCommandWrongFormatException;

/**
 * Represents an Income made of a certain income amount earned
 *
 * @author Lai Kai Jie Jeremy
 * @since 2025-10-13
 */
public class Income {
    public static int numberOfIncomes = 0;
    protected String description;
    protected float amountEarned;

    /**
     * Constructor for income
     *
     * @param description          String description of the loan
     * @param amountEarned         String of amount earned
     */
    public Income(String description, String amountEarned) throws AddIncomeCommandWrongFormatException {
        this.description = description;

        try{
            this.amountEarned = Float.parseFloat(amountEarned);
        }catch(NumberFormatException e){
            throw new AddIncomeCommandWrongFormatException();
        }
    }

    /**
     * Returns description variable
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns amount earned variable
     *
     * @return amountEarned
     */
    public float getAmountEarned() {
        return amountEarned;
    }

    /**
     * Sets amountEarned
     */
    public void setAmountEarned(float amountEarned) {
        this.amountEarned = amountEarned;
    }

    /**
     * Returns String output of description and amount earned
     *
     * @return String output of description and amount earned
     */
    public String toString() {
        StringBuilder outputStringBuilder = new StringBuilder();

        outputStringBuilder.append("\nDescription: ").append(description);
        outputStringBuilder.append("\nAmount: $").append(String.format("%.2f", amountEarned));

        return outputStringBuilder.toString();
    }
}
