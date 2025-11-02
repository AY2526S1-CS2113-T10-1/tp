package finsight.ui;

import java.util.ArrayList;
import java.util.Scanner;

import finsight.income.Income;
import finsight.expense.Expense;
import finsight.investment.Investment;
import finsight.loan.Loan;

/**
 * Outputs on the terminal and
 * takes input from the user
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @author Goh Bin Wee
 * @since 2025-10-08
 */
public class Ui {
    // Create Constants
    protected static final int LENGTH_OF_SPACING = 80;
    protected static final String SPACING = "-".repeat(LENGTH_OF_SPACING) + "\n";
    protected static Scanner input;

    /**
     * Constructor
     */
    public Ui() {
        input = new Scanner(System.in);
    }

    /**
     * Prints all possible commands and their formats
     */
    public static void printPossibleCommands() {
        System.out.print(SPACING);
        System.out.println("LOAN");
        System.out.println("\t1. list loan");
        System.out.println("\t2. add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>");
        System.out.println("\t   where <LOAN_RETURN_DATE_AND_TIME> is of format 'DD-MM-YYYY HH:mm' and");
        System.out.println("\t   <AMOUNT_LOANED> is an integer or float of at least 1 cent");
        System.out.println("\t3. delete loan <INDEX>");
        System.out.println("\t4. loan repaid <INDEX>");
        System.out.println("\t5. loan not repaid <INDEX>");
        System.out.println("\t6. edit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>");
        System.out.println("\t   where <LOAN_RETURN_DATE_AND_TIME> is of format 'DD-MM-YYYY HH:mm' and");
        System.out.println("\t   <AMOUNT_LOANED> is an integer or float of at least 1 cent");
        System.out.println("EXPENSE");
        System.out.println("\t7. list expense");
        System.out.println("\t8. add expense d/<DESCRIPTION> a/<AMOUNT_SPENT>");
        System.out.println("\t9. delete expense <INDEX>");
        System.out.println("INCOME");
        System.out.println("\t10. list income");
        System.out.println("\t11. add income d/<DESCRIPTION> a/<AMOUNT_EARNED>");
        System.out.println("\t12. delete income <INDEX>");
        System.out.println("\t13. edit income <INDEX> d/<DESCRIPTION> a/<AMOUNT_EARNED>");
        System.out.println("\t14. list income overview");
        System.out.println("INVESTMENT");
        System.out.println("\t15. list investment");
        System.out.println("\t16. add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED> m/<DEPOSIT_DATE_EACH_MONTH>");
        System.out.println("\t17. delete investment <INDEX>");
        System.out.println("18. help");
        System.out.println("19. bye");
        System.out.print(SPACING);
    }

    /**
     * Returns next line of input by the user
     *
     * @return User Input
     */
    public static String getNextLine() {
        return input.nextLine().trim();
    }

    /**
     * Prints the given Error message
     *
     * @param message Error Message
     */
    public static void printErrorMessage(String message) {
        System.out.print(SPACING);
        System.out.println(message);
        System.out.print(SPACING);
    }

    /**
     * Prints Welcome Message
     */
    public static void printWelcomeMessage() {
        System.out.println("Welcome to FinSight, what can I do for you?");
        System.out.print(SPACING);
    }

    /**
     * Prints Bye Message
     */
    public static void printByeMessage() {
        System.out.print(SPACING);
        System.out.println("Goodbye, see you again!");
        System.out.print(SPACING);
    }

    // @@author Emannuel-Tan

    /**
     * Prints the details of the loan to be deleted
     *
     * @param loan Loan to Delete
     */
    public static void printDeleteLoanOutput(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Deleted Loan:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan added
     *
     * @param loan Loan Added
     */
    public static void printAddLoanOutput(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Added Loan:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the edited loan
     *
     * @param loan Loan Edited
     */
    public static void printEditLoanOutput(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Edited Loan:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan set as repaid
     *
     * @param loan Loan set as repaid
     */
    public static void printLoanRepaid(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Set Loan as Repaid:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan set as not repaid
     *
     * @param loan Loan set as not repaid
     */
    public static void printLoanNotRepaid(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Set Loan as Not Repaid:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints all Loans
     *
     * @param loans ArrayList of Loans
     */
    public static void printAllLoans(ArrayList<Loan> loans) {
        System.out.print(SPACING);
        Double totalLoaned = 0.0;
        for (int i = 0; i < Loan.numberOfLoans; i++) {
            System.out.println("Loan " + (i + 1) + ":");
            System.out.println(loans.get(i).toString());

            if (!loans.get(i).isRepaid()) {
                totalLoaned += loans.get(i).getAmountLoaned();
            }

            System.out.print(SPACING);
        }
        System.out.println("Total loaned: $" + String.format("%.2f", totalLoaned));
        System.out.print(SPACING);
    }

    /**
     * Prints a reminder if loan to add / to be edited into already exists in the list
     *
     * @param description Description of the loan to add / to be edited into
     */
    public static void printLoanAlreadyInListReminder(String description) {
        System.out.print(SPACING);
        System.out.println("Reminder: \"" + description + "\" is already in the list.");
    }
    // @@author

    /**
     * Prints all items in income list
     *
     * @param incomes ArrayList of incomes
     */
    public static void printAllIncomes(ArrayList<Income> incomes) {
        System.out.print(SPACING);
        for (int i = 0; i < incomes.size(); i++) {
            System.out.println("Income " + (i + 1) + ":");
            System.out.println(incomes.get(i).toString());
            System.out.print(SPACING);
        }
    }

    /**
     * Prints the details of the income added
     *
     * @param income Income added
     */
    public static void printAddIncomeOutput(Income income) {
        System.out.print(SPACING);
        System.out.println("Added Income:");
        System.out.println(income.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the income to be deleted
     *
     * @param incomes       ArrayList of incomes
     * @param indexToDelete Index of income to delete
     */
    public static void printDeleteIncomeOutput(ArrayList<Income> incomes, int indexToDelete) {
        System.out.print(SPACING);
        System.out.println("Deleted Income:");
        System.out.println(incomes.get(indexToDelete).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of edited income
     *
     * @param incomes     ArrayList of incomes
     * @param indexToEdit Index of income to edit
     */
    public static void printEditIncomeOutput(ArrayList<Income> incomes, int indexToEdit) {
        System.out.print(SPACING);
        System.out.println("Edited Income:");
        System.out.println(incomes.get(indexToEdit).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the total amount of all the incomes,
     * total amount of expenses and remaining income
     *
     * @param totalIncome  Total amount of incomes
     * @param totalExpense Total amount of expenses
     */
    public static void printIncomeOverview(float totalIncome, float totalExpense) {
        System.out.print(SPACING);
        System.out.println("Total Income: $ " + totalIncome);
        System.out.println("Total Expense: $ " + totalExpense);
        System.out.println("Remaining Income: $ " + (totalIncome - totalExpense));
        System.out.print(SPACING);
    }

    /**
     * Prints all expenses
     *
     * @param expenses ArrayList of Expenses
     */
    public static void printAllExpenses(ArrayList<Expense> expenses) {
        System.out.print(SPACING);
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println("Expense " + (i + 1) + ":");
            System.out.println(expenses.get(i).toString());
            System.out.print(SPACING);
        }
    }

    /**
     * Prints the details of the expense added
     *
     * @param expenses ArrayList of expenses
     */
    public static void printAddExpenseOutput(ArrayList<Expense> expenses) {
        System.out.print(SPACING);
        System.out.println("Added Expense:");
        System.out.println(expenses.get(expenses.size() - 1).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the expense to be deleted
     *
     * @param expenses      ArrayList of expenses
     * @param indexToDelete Index of expense to delete
     */
    public static void printDeleteExpenseOutput(ArrayList<Expense> expenses, int indexToDelete) {
        System.out.print(SPACING);
        System.out.println("Deleted Expense:");
        System.out.println(expenses.get(indexToDelete).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints all investments
     *
     * @param investments ArrayList of investments
     */
    public static void printAllInvestments(ArrayList<Investment> investments) {
        System.out.print(SPACING);
        for (int i = 0; i < investments.size(); i++) {
            System.out.println("Investment " + (i + 1) + ":");
            System.out.println(investments.get(i).toString());
            System.out.print(SPACING);
        }
    }

    /**
     * Prints the overall returns of all Investment objects
     *
     * @param total5YearReturns  Overall returns of all investments in 5 years
     * @param total10YearReturns Overall returns of all investments in 10 years
     */
    public static void printInvestmentReturns(String total5YearReturns, String total10YearReturns) {
        System.out.println("Overall returns after 5 Years: $" + total5YearReturns);
        System.out.println("Overall returns after 10 Years: $" + total10YearReturns);
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the newly added investment
     *
     * @param investments ArrayList of investments
     */
    public static void printAddInvestmentOutput(ArrayList<Investment> investments) {
        System.out.print(SPACING);
        System.out.println("Added Investment:");
        System.out.println(investments.get(investments.size() - 1).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the newly deleted investment
     *
     * @param investments   ArrayList of investments
     * @param indexToDelete Index of investment in ArrayList to delete
     */
    public static void printDeleteInvestmentOutput(ArrayList<Investment> investments, int indexToDelete) {
        System.out.print(SPACING);
        System.out.println("Deleted Investment:");
        System.out.println(investments.get(indexToDelete).toString());
        System.out.print(SPACING);
    }

    public static void printEmptyExpenses() {
        System.out.print(SPACING);
        System.out.println("There is no expenses found");
        System.out.print(SPACING);
    }

    public static void printEmptyLoans() {
        System.out.print(SPACING);
        System.out.println("There is no loans found");
        System.out.print(SPACING);
    }

    public static void printEmptyIncome() {
        System.out.print(SPACING);
        System.out.println("There is no income found");
        System.out.print(SPACING);
    }

    public static void printEmptyInvestment() {
        System.out.print(SPACING);
        System.out.println("There is no investment found");
        System.out.print(SPACING);
    }

    public static void printInvalidCommandMessage() {
        System.out.print(SPACING);
        System.out.println("Invalid Command. Use `help` to show all available commands");
        System.out.print(SPACING);
    }
}
