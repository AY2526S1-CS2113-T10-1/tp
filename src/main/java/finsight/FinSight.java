package finsight;

import java.util.ArrayList;

import loan.Loan;
import loan.loanlist.LoanList;
import parser.Parser;
import ui.Ui;

/**
 * <h1>FinSight</h1>
 * FinSight is a
 *
 * @author Emannuel Tan Jing Yue
 * @version 0.1
 * @since 2025-10-08
 */
public class FinSight {
    protected static ArrayList<Loan> loans = new ArrayList<>();

    /**
     * Main entry-point for the FinSight application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        LoanList loanList = new LoanList(ui);
        Parser parser = new Parser(loanList, ui);

        ui.printWelcomeMessage();

        String userInput = ui.getNextLine();
        while (!userInput.startsWith("bye")) {
            parser.tryCommand(userInput);

            userInput = ui.getNextLine();
        }

        ui.printByeMessage();
    }
}
