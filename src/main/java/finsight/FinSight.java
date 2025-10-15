package finsight;

import finsight.income.incomelist.IncomeList;
import finsight.expense.expenselist.ExpenseList;
import finsight.investment.investmentlist.InvestmentList;
import finsight.loan.loanlist.LoanList;
import finsight.parser.Parser;
import finsight.ui.Ui;

/**
 * <h1>FinSight</h1>
 * FinSight is a
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @author L'kesh Nair
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
        ExpenseList expenseList = new ExpenseList(ui);
        IncomeList incomeList = new IncomeList(ui);
        InvestmentList investmentList = new InvestmentList(ui);
        LoanList loanList = new LoanList(ui);
        Parser parser = new Parser(expenseList,incomeList,investmentList,loanList,ui);

        ui.printWelcomeMessage();

        String userInput = ui.getNextLine();
        while (!userInput.toLowerCase().startsWith("bye")) {
            parser.tryCommand(userInput);

            userInput = ui.getNextLine();
        }

        ui.printByeMessage();
    }
}
