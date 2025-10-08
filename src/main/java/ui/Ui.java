package ui;

import java.util.ArrayList;
import java.util.Scanner;

import loan.Loan;

/**
 * Outputs on the terminal and
 * takes input from the user
 *
 * @author Emannuel Tan Jing Yue
 * @since 2025-10-08
 */
public class Ui {
    // Create Constants
    protected final int LENGTH_OF_SPACING = 70;
    protected final String SPACING = "-".repeat(LENGTH_OF_SPACING) + "\n";
    protected final Scanner input = new Scanner(System.in);

    /**
     * Returns next line of input by the user
     *
     * @return User Input
     */
    public String getNextLine() {
        return input.nextLine().trim();
    }

    /**
     * Prints the given Error message
     *
     * @param message Error Message
     */
    public void printErrorMessage(String message) {
        System.out.print(SPACING);
        System.out.println(message);
        System.out.print(SPACING);
    }

    /**
     * Prints Welcome Message
     */
    public void printWelcomeMessage() {
        System.out.println("Welcome to FinSight, what can i do for you?");
        System.out.print(SPACING);
    }

    /**
     * Prints Bye Message
     */
    public void printByeMessage() {
        System.out.print(SPACING);
        System.out.println("Goodbye, see you again!");
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan to be deleted
     *
     * @param loans         ArrayList of loans
     * @param indexToDelete Index of loan to delete
     */
    public void printDeleteLoanOutput(ArrayList<Loan> loans, int indexToDelete) {
        System.out.print(SPACING);
        System.out.println("Deleted Loan:");
        System.out.println(loans.get(indexToDelete).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan added
     *
     * @param loans ArrayList of loans
     */
    public void printAddLoanOutput(ArrayList<Loan> loans) {
        System.out.print(SPACING);
        System.out.println("Added Loan:");
        System.out.println(loans.get(Loan.numberOfLoans).toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan set as repaid
     */
    public void printLoanRepaid(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Set Loan as Repaid:");
        System.out.println(loan.toString());
        System.out.print(SPACING);
    }

    /**
     * Prints the details of the loan set as not repaid
     */
    public void printLoanNotRepaid(Loan loan) {
        System.out.print(SPACING);
        System.out.println("Set Loan as Not Repaid:");
        System.out.println(loan.toString());
        System.out.println(SPACING);
    }

    /**
     * Prints all Loans
     */
    public void printAllLoans(ArrayList<Loan> loans) {
        System.out.print(SPACING);
        for (int i = 0; i < Loan.numberOfLoans; i++) {
            System.out.println("Loan " + (i + 1) + ":");
            System.out.println(loans.get(i).toString());
            System.out.print(SPACING);
        }
    }

    /**
     * Prints all possible commands and their formats
     */
    public void printPossibleCommands() {
        System.out.print(SPACING);
        System.out.println("Invalid command. Please use one of the following commands:");
        System.out.println("1. list loan");
        System.out.println("2. add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>");
        System.out.println("   where <LOAN_RETURN_DATE_AND_TIME> is of format 'DD-mm-YYYY HH:mm'");
        System.out.println("3. delete loan <INDEX>");
        System.out.print(SPACING);
    }
}
