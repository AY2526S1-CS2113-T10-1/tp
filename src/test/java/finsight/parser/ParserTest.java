package finsight.parser;

import finsight.expense.Expense;
import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import finsight.expense.exceptions.DeleteExpenseCommandIndexOutOfBoundsException;
import finsight.expense.expenselist.ExpenseList;

import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.income.exceptions.DeleteIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandWrongFormatException;
import finsight.income.Income;
import finsight.income.incomelist.IncomeList;

import finsight.investment.exceptions.AddInvestmentSubcommandException;
import finsight.investment.exceptions.AddInvestmentSubcommandOrderException;
import finsight.investment.Investment;
import finsight.investment.investmentlist.InvestmentList;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandInvalidAmountException;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.EditLoanCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.EditLoanCommandInvalidAmountException;
import finsight.loan.exceptions.EditLoanCommandWrongFormatException;
import finsight.loan.exceptions.LoanRepaidCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.LoanNotRepaidCommandIndexOutOfBoundsException;
import finsight.loan.loanlist.LoanList;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserTest {
    ExpenseList expenseList;
    IncomeList incomeList;
    InvestmentList investmentList;
    LoanList loanList;
    Parser parser;

    @BeforeEach
    void setUp() throws IOException {
        expenseList = new ExpenseList();
        incomeList = new IncomeList();
        investmentList = new InvestmentList();
        loanList = new LoanList();
        parser = new Parser(expenseList, incomeList, investmentList, loanList);

        int loopCountExpense = Expense.numberOfExpenses;
        for (int i = 0; i < loopCountExpense; i++) {
            expenseList.deleteExpense(0);
        }
        int loopCountIncome = Income.numberOfIncomes;
        for (int i = 0; i < loopCountIncome; i++) {
            incomeList.deleteIncome(0);
        }
        int loopCountInvestment = Investment.numberOfInvestments;
        for (int i = 0; i < loopCountInvestment; i++) {
            investmentList.deleteInvestment(0);
        }
        int loopCountLoan = Loan.numberOfLoans;
        for (int i = 0; i < loopCountLoan; i++) {
            loanList.deleteLoan(0);
        }
    }

    // @@author Emannuel-Tan
    @Test
    void parseAddLoanCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        String inputTestString = "add loan a/ 1000 d/ loan r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeASubcommand_exceptionThrown() {
        String inputTestString = "add loan r/ 10-10-2126 19:00 d/ loan a/ 1000";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_rSubcommandBeforeDSubcommand_exceptionThrown() {
        String inputTestString = "add loan r/ 10-10-2126 19:00 d/ loan a/1000";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDescription_exceptionThrown() {
        String inputTestString = "add loan d/ a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingAmount_exceptionThrown() {
        String inputTestString = "add loan d/ loan a/ r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDateTime_exceptionThrown() {
        String inputTestString = "add loan d/ loan a/ 1000 r/";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingASubcommand_exceptionThrown() {
        String inputTestString = "add loan d/ loan 1000 r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingDSubcommand_exceptionThrown() {
        String inputTestString = "add loan loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_missingRSubcommand_exceptionThrown() {
        String inputTestString = "add loan d/ loan a/ 1000 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_dateWrongFormat_exceptionThrown() {
        String inputTestString = "add loan d/ loan a/ 1000 r/ 10-10-2126";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseAddLoanCommand_amountContainsAlphabets_exceptionThrown() {
        String inputTestString = "add loan d/ loan a/ a r/ 10-10-2126 19:00";

        assertThrows(AddLoanCommandWrongFormatException.class,
                () -> parser.parseAddLoanCommand(inputTestString));
    }

    /**
     * Tests if add loan command is used with
     * 1. amount > 0 and < 0.01
     * 2. amount = 0
     * 3. amount < 0
     */
    @Test
    void parseAddLoanCommand_amountLesserThanOneCent_exceptionThrown() {
        String inputTestStringSmallAmount = "add loan d/loan a/0.001 r/10-10-2126 19:00";
        String inputTestStringZeroAmount = "add loan d/loan a/0 r/10-10-2126 19:00";
        String inputTestStringNegativeAmount = "add loan d/loan a/-1 r/10-10-2126 19:00";

        assertThrows(AddLoanCommandInvalidAmountException.class,
                () -> parser.parseAddLoanCommand(inputTestStringSmallAmount));
        assertThrows(AddLoanCommandInvalidAmountException.class,
                () -> parser.parseAddLoanCommand(inputTestStringZeroAmount));
        assertThrows(AddLoanCommandInvalidAmountException.class,
                () -> parser.parseAddLoanCommand(inputTestStringNegativeAmount));
    }

    @Test
    void parseAddLoanCommand_correctInputs_noExceptionThrown() {
        String inputTestString = "add loan d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertDoesNotThrow(() -> parser.parseAddLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_missingIndex_exceptionThrown() {
        String inputTestString = "delete loan";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_negativeIndex_exceptionThrown() {
        String inputTestString = "delete loan -1";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_indexGreaterThanAmountOfLoans_exceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "delete loan 2";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_correctIndex_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "delete loan 1";

        assertDoesNotThrow(() -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_alphabetsInIndex_exceptionThrown() {
        String inputTestString = "delete loan abc";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseDeleteLoanCommand_zeroIndex_exceptionThrown() {
        String inputTestString = "delete loan 0";

        assertThrows(DeleteLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteLoanCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_missingIndex_exceptionThrown() {
        String inputTestString = "loan repaid";

        assertThrows(LoanRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_zeroIndex_exceptionThrown() {
        String inputTestString = "loan repaid 0";

        assertThrows(LoanRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_negativeIndex_exceptionThrown() {
        String inputTestString = "loan repaid -1";

        assertThrows(LoanRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_indexContainsAlphabet_exceptionThrown() {
        String inputTestString = "loan repaid a";

        assertThrows(LoanRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_indexGreaterThanAmountOfLoans_exceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "loan repaid 2";

        assertThrows(LoanRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanRepaidCommand_correctIndex_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "loan repaid 1";

        assertDoesNotThrow(() -> parser.parseLoanRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_negativeIndex_exceptionThrown() {
        String inputTestString = "loan not repaid -1";

        assertThrows(LoanNotRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_zeroIndex_exceptionThrown() {
        String inputTestString = "loan not repaid 0";

        assertThrows(LoanNotRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_missingIndex_exceptionThrown() {
        String inputTestString = "loan not repaid";

        assertThrows(LoanNotRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_indexContainsAlphabets_exceptionThrown() {
        String inputTestString = "loan not repaid a";

        assertThrows(LoanNotRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_indexGreaterThanAmountOfLoans_exceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "loan not repaid 2";

        assertThrows(LoanNotRepaidCommandIndexOutOfBoundsException.class,
                () -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseLoanNotRepaidCommand_correctIndex_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "loan not repaid 1";

        assertDoesNotThrow(() -> parser.parseLoanNotRepaidCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_indexContainsAlphabets_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan a d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_zeroIndex_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 0 d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_negativeIndex_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan -1 d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_indexGreaterThanAmountOfLoans_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "edit loan 2 d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingIndex_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_aSubcommandBeforeDSubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 a/ 1000 d/ loan r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_rSubcommandBeforeASubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 r/ 10-10-2126 19:00 d/ loan a/ 1000";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_rSubcommandBeforeDSubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 r/ 10-10-2126 19:00 d/ loan a/1000";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingDescription_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingAmount_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingDateTime_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ 1000 r/";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingASubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingDSubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 loan a/ 1000 r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_missingRSubcommand_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ 1000 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_dateWrongFormat_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ 1000 r/ 10-10-2126";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    @Test
    void parseEditLoanCommand_amountContainsAlphabets_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ a r/ 10-10-2126 19:00";

        assertThrows(EditLoanCommandWrongFormatException.class,
                () -> parser.parseEditLoanCommand(inputTestString));
    }

    /**
     * Tests if edit loan command is used with
     * 1. amount > 0 and < 0.01
     * 2. amount = 0
     * 3. amount < 0
     */
    @Test
    void parseEditLoanCommand_amountLesserThanOneCent_exceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestStringSmallAmount = "edit loan 1 d/loan a/0.001 r/10-10-2126 19:00";
        String inputTestStringZeroAmount = "edit loan 1 d/loan a/0 r/10-10-2126 19:00";
        String inputTestStringNegativeAmount = "edit loan 1 d/loan a/-1 r/10-10-2126 19:00";

        assertThrows(EditLoanCommandInvalidAmountException.class,
                () -> parser.parseEditLoanCommand(inputTestStringSmallAmount));
        assertThrows(EditLoanCommandInvalidAmountException.class,
                () -> parser.parseEditLoanCommand(inputTestStringZeroAmount));
        assertThrows(EditLoanCommandInvalidAmountException.class,
                () -> parser.parseEditLoanCommand(inputTestStringNegativeAmount));
    }

    @Test
    void parseEditLoanCommand_correctInputs_noExceptionThrown() throws IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 18:00"));
        String inputTestString = "edit loan 1 d/ loan a/ 1000 r/ 10-10-2126 19:00";

        assertDoesNotThrow(() -> parser.parseEditLoanCommand(inputTestString));
    }
    // @@author

    @Test
    void parseAddIncomeCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        String inputTestString = "add income a/ 1000 d/ Salary";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseAddIncomeCommand_missingDescription_exceptionThrown() {
        String inputTestString = "add income d/ a/ 1000";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseAddIncomeCommand_missingAmount_exceptionThrown() {
        String inputTestString = "add income d/ Salary a/";

        assertThrows(AddIncomeCommandWrongFormatException.class,
                () -> parser.parseAddIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_missingIndex_exceptionThrown() {
        String inputTestString = "delete income";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_negativeIndex_exceptionThrown() {
        String inputTestString = "delete income -1";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseDeleteIncomeCommand_zeroIndex_exceptionThrown() {
        String inputTestString = "delete income 0";

        assertThrows(DeleteIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        String inputTestString = "edit income 1 a/ 10 d/ Salary";

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingIndex_exceptionThrown() {
        String inputTestString = "edit income d/ Salary a/ 10";

        assertThrows(EditIncomeCommandIndexOutOfBoundsException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingDescription_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        String inputTestString = "edit income 1 d/ a/ 10";

        incomeList.addIncome(new Income("Salary", "1000"));

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseEditIncomeCommand_missingAmount_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        String inputTestString = "edit income 1 d/ Salary a/";

        incomeList.addIncome(new Income("Salary", "1000"));

        assertThrows(EditIncomeCommandWrongFormatException.class,
                () -> parser.parseEditIncomeCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_missingDescription_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        String inputTestString = "add investment d/ a/100 m/20";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_missingAmount_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        String inputTestString = "add investment d/Test a/ m/20";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_missingRecurringDate_exceptionThrown() throws AddIncomeCommandWrongFormatException,
            IOException {
        String inputTestString = "add investment d/Test a/100 m/";

        assertThrows(AddInvestmentSubcommandException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_validOrder_noExceptionThrown() {
        String inputTestString = "add investment d/Test a/100 r/1.0 m/20";

        assertDoesNotThrow(
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_aSubcommandBeforeDSubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException,
            IOException {
        String inputTestString = "add investment a/100 d/Test r/1.0 m/20";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_rSubcommandBeforeASubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        String inputTestString = "add investment d/Test r/1.0 a/100 m/20";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }

    @Test
    void parseAddInvestmentCommand_mSubcommandBeforeRSubcommand_exceptionThrown()
            throws AddIncomeCommandWrongFormatException, IOException {
        String inputTestString = "add investment d/Test a/100 m/20 r/1.0";

        assertThrows(AddInvestmentSubcommandOrderException.class,
                () -> parser.parseAddInvestmentCommand(inputTestString));
    }


    @Test
    void tryCommand_addLoan_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        String inputTestString = "add loan d/ 1 a/ 1000 r/ 12-12-2126 19:00";

        assertDoesNotThrow(() -> parser.tryCommand(inputTestString));
        loanList.deleteLoan(0);
    }


    @Test
    void parseAddExpenseCommand_emptyInput_exceptionThrown() {
        String inputTestString = "add expense";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void tryCommand_listLoan_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        String inputTestString = "list loan";

        assertDoesNotThrow(() -> parser.tryCommand(inputTestString));
    }


    @Test
    void parseAddExpenseCommand_missingDescription_exceptionThrown() {
        String inputTestString = "add expense d/ a/ 1000 ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void tryCommand_deleteLoan_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "delete loan 1";

        assertDoesNotThrow(() -> parser.tryCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingExpenseAmount_exceptionThrown() {
        String inputTestString = "add expense d/ food a/ ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void tryCommand_setLoanRepaid_noExceptionThrown()
            throws AddLoanCommandWrongFormatException, IOException {
        loanList.addLoan(new Loan("1", "1000", "12-12-2126 19:00"));
        String inputTestString = "loan repaid 1";

        assertDoesNotThrow(() -> parser.tryCommand(inputTestString));
        loanList.deleteLoan(0);
    }


    @Test
    void parseAddExpenseCommand_missingDescriptionAndExpenseAmount_exceptionThrown() {
        String inputTestString = "add expense d/ a/ ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }


    @Test
    void tryCommand_addLoanWrongInputs_exceptionThrownAndCaught()
            throws AddLoanCommandWrongFormatException, IOException {
        String inputTestString = "add loan d/ 1 a/1000 r/";

        assertDoesNotThrow(() -> parser.tryCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingASubcommand_exceptionThrown() {
        String inputTestString = "add expense d/ food 1000 ";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_missingDSubcommand_exceptionThrown() {
        String inputTestString = "add expense food a/ 1000";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseAddExpenseCommand_aSubcommandBeforeDSubcommand_exceptionThrown() {
        String inputTestString = "add expense a/ 1000 d/ food";

        assertThrows(AddExpenseCommandWrongFormatException.class,
                () -> parser.parseAddExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_emptyInput_exceptionThrown() {
        String inputTestString = "delete expense";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_negativeIndex_exceptionThrown() {
        String inputTestString = "delete expense -5";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }

    @Test
    void parseDeleteExpenseCommand_zeroIndex_exceptionThrown() {
        String inputTestString = "delete expense 0";

        assertThrows(DeleteExpenseCommandIndexOutOfBoundsException.class,
                () -> parser.parseDeleteExpenseCommand(inputTestString));
    }

}
