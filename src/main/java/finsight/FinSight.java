package finsight;

import finsight.expense.expenselist.ExpenseList;
import finsight.loan.loanlist.LoanList;
import finsight.parser.Parser;
import finsight.storage.LoanDataManager;
import finsight.ui.Ui;

import java.io.IOException;

/**
 * <h1>FinSight</h1>
 * FinSight is a
 *
 * @author Emannuel Tan Jing Yue
 * @version 0.1
 * @since 2025-10-08
 */
public class FinSight {
    /**
     * Main entry-point for the FinSight application.
     */
    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();
        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        ui.printWelcomeMessage();

        String userInput = ui.getNextLine();
        while (!userInput.startsWith("bye")) {
            parser.tryCommand(userInput);

            userInput = ui.getNextLine();
        }

        ui.printByeMessage();
    }
}
