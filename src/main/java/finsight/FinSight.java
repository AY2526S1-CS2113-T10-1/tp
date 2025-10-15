package finsight;

import finsight.income.incomelist.IncomeList;
import finsight.expense.expenselist.ExpenseList;
import finsight.loan.loanlist.LoanList;
import finsight.parser.Parser;
import finsight.ui.Ui;

/**
 * <h1>FinSight</h1>
 * FinSight is a
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @author Goh Bin Wee
 * @version 1.0
 * @since 2025-10-08
 */
public class FinSight {
    /**
     * Main entry-point for the FinSight application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        LoanList loanList = new LoanList(ui);
        IncomeList incomeList = new IncomeList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, incomeList, expenseList, ui);

        ui.printWelcomeMessage();

        String userInput = ui.getNextLine();
        while (!userInput.toLowerCase().startsWith("bye")) {
            parser.tryCommand(userInput);

            userInput = ui.getNextLine();
        }

        ui.printByeMessage();
    }
}
