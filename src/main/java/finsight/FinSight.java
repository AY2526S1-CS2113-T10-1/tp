package finsight;

import finsight.income.incomelist.IncomeList;
import finsight.loan.loanlist.LoanList;
import finsight.parser.Parser;
import finsight.ui.Ui;

/**
 * <h1>FinSight</h1>
 * FinSight is a
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @version 0.1
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
        Parser parser = new Parser(loanList,incomeList, ui);

        ui.printWelcomeMessage();

        String userInput = ui.getNextLine();
        while (!userInput.startsWith("bye")) {
            parser.tryCommand(userInput);

            userInput = ui.getNextLine();
        }

        ui.printByeMessage();
    }
}
