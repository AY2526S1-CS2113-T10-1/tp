package finsight.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import finsight.investment.exceptions.AddInvestmentSubcommandException;
import finsight.investment.exceptions.AddInvestmentSubcommandOrderException;
import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import finsight.expense.exceptions.DeleteExpenseCommandIndexOutOfBoundsException;
import finsight.investment.investmentlist.InvestmentList;
import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.income.exceptions.DeleteIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandWrongFormatException;
import finsight.income.incomelist.IncomeList;

import org.junit.jupiter.api.Test;

import finsight.expense.expenselist.ExpenseList;

import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;
import finsight.loan.loanlist.LoanList;

import finsight.ui.Ui;

import java.io.IOException;

public class ParserTest {
    @Test
    void parseAddLoanCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add loan a/ 1000 d/ loan r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeASubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan r/ 10-10-2026 19:00 d/ loan a/ 1000";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan a/ 1000 r/ 10-10-2026 19:00 d/ loan";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDescription_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan d/ a/ 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingAmount_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan d/ loan a/ r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDateTime_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan d/ loan a/ 1000 r/";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingASubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan d/ loan 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan loan a/ 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingRSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add loan d/ loan a/ 1000 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_missingIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete loan";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_negativeIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete loan -1";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_zeroIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete loan 0";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseAddIncomeCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add income a/ 1000 d/ Salary";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseAddIncomeCommand_missingDescription_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add income d/ a/ 1000";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseAddIncomeCommand_missingAmount_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add income d/ Salary a/";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_missingIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete income";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_negativeIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete income -1";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_zeroIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "delete income 0";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "edit income 1 a/ 10 d/ Salary";

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "edit income d/ Salary a/ 10";

        assertThrows(EditIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingDescription_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "edit income 1 d/ a/ 10";

        incomeList.addIncome(new Income("Salary", "1000"));

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingAmount_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "edit income 1 d/ Salary a/";

        incomeList.addIncome(new Income("Salary", "1000"));

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_missingDescription_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/ a/100 m/20";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_missingAmount_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/Test a/ m/20";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_missingRecurringDate_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/Test a/100 m/";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_validOrder_noExceptionThrown() {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/Test a/100 r/1.0 m/20";

        assertDoesNotThrow(
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_aSubcommandBeforeDSubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException,
            IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment a/100 d/Test r/1.0 m/20";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_rSubcommandBeforeASubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/Test r/1.0 a/100 m/20";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }
    @Test
    void parseAddInvestmentCommand_mSubcommandBeforeRSubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);

        String inputTestString = "add investment d/Test a/100 m/20 r/1.0";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_emptyInput_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }
    @Test
    void parseAddExpenseCommand_missingDescription_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense d/ a/ 1000 ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingExpenseAmount_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense d/ food a/ ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingDescriptionAndExpenseAmount_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense d/ a/ ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingASubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense d/ food 1000 ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense food a/ 1000";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "add expense a/ 1000 d/ food";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_emptyInput_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "delete expense";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_negativeIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "delete expense -5";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_zeroIndex_exceptionThrown() {
        Ui ui = new Ui();
        ExpenseList expenseList = new ExpenseList();
        IncomeList incomeList = new IncomeList();
        InvestmentList investmentList = new InvestmentList();
        LoanList loanList = new LoanList();
        Parser parser = new Parser(expenseList, incomeList, investmentList, loanList);


        String inputTestString = "delete expense 0";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }


}
