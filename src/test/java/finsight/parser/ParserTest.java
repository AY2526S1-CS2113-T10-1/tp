package finsight.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
//import finsight.expense.exceptions.DeleteExpenseCommandIndexOutOfBoundsException;
import finsight.expense.expenselist.ExpenseList;

import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;
import finsight.loan.loanlist.LoanList;

import finsight.ui.Ui;

import java.io.IOException;

public class ParserTest {
    @Test
    void parseAddLoanCommand_aSubcommandBeforeDSubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan a/ 1000 d/ loan r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeASubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan r/ 10-10-2026 19:00 d/ loan a/ 1000";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeDSubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan a/ 1000 r/ 10-10-2026 19:00 d/ loan";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDescription_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan d/ a/ 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingAmount_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan d/ loan a/ r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDateTime_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan d/ loan a/ 1000 r/";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingASubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan d/ loan 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDSubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan loan a/ 1000 r/ 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingRSubcommand_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "add loan d/ loan a/ 1000 10-10-2026 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_missingIndex_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "delete loan";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_negativeIndex_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "delete loan -1";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_zeroIndex_exceptionThrown() throws IOException {
        Ui ui = new Ui();

        LoanList loanList = new LoanList(ui);
        ExpenseList expenseList = new ExpenseList(ui);
        Parser parser = new Parser(loanList, ui, expenseList);

        String inputTestString = "delete loan 0";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }
}
