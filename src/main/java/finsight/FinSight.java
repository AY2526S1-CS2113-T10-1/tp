package finsight;

import finsight.income.incomelist.IncomeList;
import finsight.expense.expenselist.ExpenseList;
import finsight.investment.investmentlist.InvestmentList;
import finsight.loan.loanlist.LoanList;
import finsight.parser.Parser;
import finsight.ui.Ui;

/**
 * <h1>FinSight</h1>
 * FinSight is a CLI-based app for managing finances such as income, expenses, loans and investments.
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @author L'kesh Nair
 * @author Goh Bin Wee
 * @version 2.1
 * @since 2025-10-08
 */
public class FinSight {
    /**
     * Main entry-point for the FinSight application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser(new ExpenseList(), new IncomeList(), new InvestmentList(), new LoanList());

        Ui.printWelcomeMessage();

        String userInput = Ui.getNextLine();
        while (!userInput.toLowerCase().startsWith("bye")) {
            parser.tryCommand(userInput);
            userInput = Ui.getNextLine();
        }
        Ui.printByeMessage();
    }
}
