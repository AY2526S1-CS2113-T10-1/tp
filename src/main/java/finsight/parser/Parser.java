package finsight.parser;

import finsight.expense.Expense;
import finsight.expense.exceptions.AddExpenseCommandWrongFormatException;
import finsight.expense.exceptions.DeleteExpenseCommandIndexOutOfBoundsException;
import finsight.expense.expenselist.ExpenseList;

import finsight.investment.Investment;
import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentSubcommandException;
import finsight.investment.exceptions.AddInvestmentSubcommandOrderException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;
import finsight.investment.exceptions.DeleteInvestmentIndexOutOfBoundsException;
import finsight.investment.exceptions.DeleteInvestmentMissingIndexException;
import finsight.investment.exceptions.DeleteInvestmentWrongNumberFormatException;
import finsight.investment.investmentlist.InvestmentList;
import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.income.exceptions.DeleteIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandWrongFormatException;
import finsight.income.incomelist.IncomeList;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.LoanRepaidCommandIndexOutOfBoundsException;
import finsight.loan.Loan;
import finsight.loan.loanlist.LoanList;

import finsight.ui.Ui;

import java.io.IOException;

/**
 * Takes in the user input and interpret which command to run
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @author Goh Bin Wee
 * @author L'kesh Nair
 * @author Royden Lim Yi Ren
 * @since 2025-10-08
 */
public class Parser {
    protected ExpenseList expenseList;
    protected IncomeList incomeList;
    protected InvestmentList investmentList;
    protected LoanList loanList;
    protected Ui ui;


    public Parser(ExpenseList expenseList, IncomeList incomeList, InvestmentList investmentList, LoanList loanList,
                  Ui ui) {
        this.expenseList = expenseList;
        this.incomeList = incomeList;
        this.investmentList = investmentList;
        this.loanList = loanList;
        this.ui = ui;
    }

    /**
     * Try to run a command and handle any exceptions thrown
     *
     * @param userInput String input by the user
     */
    public void tryCommand(String userInput) {
        try {
            handleCommand(userInput);
        } catch (AddExpenseCommandWrongFormatException | AddLoanCommandWrongFormatException |
                 AddIncomeCommandWrongFormatException | DeleteIncomeCommandIndexOutOfBoundsException |
                 DeleteExpenseCommandIndexOutOfBoundsException | DeleteLoanCommandIndexOutOfBoundsException |
                 LoanRepaidCommandIndexOutOfBoundsException | AddInvestmentWrongNumberFormatException |
                 AddInvestmentSubcommandException | DeleteInvestmentMissingIndexException |
                 AddInvestmentDateOutOfBoundsException | DeleteInvestmentIndexOutOfBoundsException |
                 AddInvestmentSubcommandOrderException | EditIncomeCommandIndexOutOfBoundsException |
                 EditIncomeCommandWrongFormatException | DeleteInvestmentWrongNumberFormatException | IOException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    /**
     * Take in user input and run the respective command and
     * throw exceptions as necessary
     *
     * @param userInput String input by the user
     * @throws AddExpenseCommandWrongFormatException         If add expense command has empty fields, incorrect format
     *                                                       or incorrect sub commands
     * @throws AddInvestmentDateOutOfBoundsException         If add investment command has an invalid date that does not
     *                                                       fit in a calendar
     * @throws AddInvestmentSubcommandException              If add investment command has a missing or empty subcommand
     * @throws AddInvestmentSubcommandOrderException         If add investment command has subcommands
     *                                                       in the wrong order
     * @throws AddInvestmentWrongNumberFormatException       If add investment command has subcommands whose values
     *                                                       are not numeric
     * @throws AddIncomeCommandWrongFormatException          If add income command has empty fields, incorrect format
     *                                                       or incorrect sub commands
     * @throws AddLoanCommandWrongFormatException            If add loan command has empty fields or
     *                                                       missing sub commands or sub commands in wrong order or
     *                                                       date field in wrong format
     * @throws DeleteExpenseCommandIndexOutOfBoundsException If delete expense command used with out-of-bounds index
     * @throws DeleteIncomeCommandIndexOutOfBoundsException  If delete income command used with non-existing index or
     *                                                       index missing
     * @throws DeleteInvestmentIndexOutOfBoundsException     If delete investment command is used with non-existing
     *                                                       index
     * @throws DeleteInvestmentMissingIndexException         If delete investment command is used without inserting an
     *                                                       index
     * @throws DeleteLoanCommandIndexOutOfBoundsException    If delete loan command used with non-existing index or
     *                                                       index missing
     * @throws EditIncomeCommandWrongFormatException         If edit income command has empty fields, incorrect format
     *                                                       or incorrect sub commands
     * @throws EditIncomeCommandIndexOutOfBoundsException    If edit income command used with non-existing index or
     *                                                       index missing
     * @throws LoanRepaidCommandIndexOutOfBoundsException    If loan repaid command used with non-existing index or
     *                                                       index missing
     */
    public void handleCommand(String userInput)
            throws AddExpenseCommandWrongFormatException, AddInvestmentDateOutOfBoundsException,
            AddInvestmentSubcommandException, AddInvestmentSubcommandOrderException,
            AddInvestmentWrongNumberFormatException, AddIncomeCommandWrongFormatException,
            AddLoanCommandWrongFormatException, DeleteExpenseCommandIndexOutOfBoundsException,
            DeleteIncomeCommandIndexOutOfBoundsException, DeleteInvestmentIndexOutOfBoundsException,
            DeleteInvestmentMissingIndexException, DeleteInvestmentWrongNumberFormatException,
            DeleteLoanCommandIndexOutOfBoundsException, EditIncomeCommandWrongFormatException,
            EditIncomeCommandIndexOutOfBoundsException, LoanRepaidCommandIndexOutOfBoundsException, IOException {

        if (userInput.toLowerCase().startsWith("list loan")) {
            loanList.listLoans();
        } else if (userInput.toLowerCase().startsWith("add loan")) {
            String[] commandParameters = parseAddLoanCommand(userInput);
            assert (!commandParameters[0].isEmpty() && !commandParameters[1].isEmpty()
                    && !commandParameters[2].isEmpty());
            loanList.addLoan(new Loan(commandParameters[0], commandParameters[1], commandParameters[2]));
        } else if (userInput.toLowerCase().startsWith("delete loan")) {
            int indexToDelete = parseDeleteLoanCommand(userInput);
            assert (indexToDelete >= 0 && indexToDelete < Loan.numberOfLoans);
            loanList.deleteLoan(indexToDelete);
        } else if (userInput.toLowerCase().startsWith("loan repaid")) {
            int indexToSetRepaid = parseLoanRepaidCommand(userInput);
            assert (indexToSetRepaid >= 0 && indexToSetRepaid < Loan.numberOfLoans);
            loanList.setRepaid(indexToSetRepaid);
        } else if (userInput.toLowerCase().startsWith("add income")) {
            String[] commandParameters = parseAddIncomeCommand(userInput);
            incomeList.addIncome(new Income(commandParameters[0], commandParameters[1]));
        } else if (userInput.toLowerCase().startsWith("delete income")) {
            int indexToDelete = parseDeleteIncomeCommand(userInput);
            incomeList.deleteIncome(indexToDelete);
        } else if (userInput.toLowerCase().startsWith("edit income")) {
            String[] commandParameters = parseEditIncomeCommand(userInput);
            incomeList.editIncome(commandParameters[0], commandParameters[1], commandParameters[2]);
        } else if (userInput.toLowerCase().startsWith("list income")) {
            incomeList.listIncomes();
        } else if (userInput.toLowerCase().startsWith("list expense")) {
            expenseList.listExpenses();
        } else if (userInput.toLowerCase().startsWith("add expense")) {
            String[] commandParameters = parseAddExpenseCommand(userInput);
            expenseList.addExpense(new Expense(commandParameters[0], commandParameters[1]));
        } else if (userInput.toLowerCase().startsWith("delete expense")) {
            int indexToDelete = parseDeleteExpenseCommand(userInput);
            expenseList.deleteExpense(indexToDelete);
        } else if (userInput.toLowerCase().startsWith("list investment")) {
            investmentList.listAllInvestments();
        } else if (userInput.toLowerCase().startsWith("add investment")) {
            String[] commandParameters = parseAddInvestmentCommand(userInput);
            investmentList.addInvestment(new Investment(commandParameters[0],
                    commandParameters[1], commandParameters[2]));
        } else if (userInput.toLowerCase().startsWith("delete investment")) {
            int indexToDelete = parseDeleteInvestmentCommand(userInput);
            investmentList.deleteInvestment(indexToDelete);
        } else {
            ui.printPossibleCommands();
        }
    }

    /**
     * Returns the index to delete expense if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to delete
     * @throws DeleteExpenseCommandIndexOutOfBoundsException If index to delete does not exist
     */
    private int parseDeleteExpenseCommand(String userInput) throws DeleteExpenseCommandIndexOutOfBoundsException {
        final int sizeOfDeleteExpense = "delete expense".length();
        String indexToDeleteString = userInput.substring(sizeOfDeleteExpense).trim();
        if (indexToDeleteString.isEmpty()) {
            throw new DeleteExpenseCommandIndexOutOfBoundsException();
        }

        int indexToDelete;
        try {
            indexToDelete = Integer.parseInt(indexToDeleteString) - 1;
        } catch (NumberFormatException e) {
            throw new DeleteExpenseCommandIndexOutOfBoundsException();
        }

        if (indexToDelete < 0 || indexToDelete >= expenseList.getSize()) {
            throw new DeleteExpenseCommandIndexOutOfBoundsException();
        }

        return indexToDelete;
    }

    /**
     * Returns the parameters used for add expense command as a String Array of size 2
     * <pre>
     * commandParameters[0]: Description
     * commandParameters[1]: Expended amount
     * </pre>
     *
     * @param userInput String input by user
     * @return The parameters used for add expense command
     * @throws AddExpenseCommandWrongFormatException If any empty fields or wrong sub command or wrong sub command order
     */
    private String[] parseAddExpenseCommand(String userInput) throws
            AddExpenseCommandWrongFormatException {
        final int numberOfAddExpenseCommandParameters = 2;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddExpenseCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/"));
        if (hasInvalidSubcommand || hasInvalidSubcommandOrder) {
            throw new AddExpenseCommandWrongFormatException();
        }


        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty();
        if (hasInvalidParameters) {
            throw new AddExpenseCommandWrongFormatException();
        }

        return commandParameters;
    }

    /**
     * Returns the index to delete if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to delete
     * @throws DeleteLoanCommandIndexOutOfBoundsException If index to delete does not exist or missing
     */
    public int parseDeleteLoanCommand(String userInput) throws DeleteLoanCommandIndexOutOfBoundsException {
        final int sizeOfDeleteLoan = "delete loan".length();
        String indexToDeleteString = userInput.substring(sizeOfDeleteLoan).trim();
        if (indexToDeleteString.isEmpty()) {
            throw new DeleteLoanCommandIndexOutOfBoundsException();
        }

        int indexToDelete;
        try {
            indexToDelete = Integer.parseInt(indexToDeleteString) - 1;
        } catch (NumberFormatException e) {
            throw new DeleteLoanCommandIndexOutOfBoundsException();
        }

        if (indexToDelete < 0 || indexToDelete >= Loan.numberOfLoans) {
            throw new DeleteLoanCommandIndexOutOfBoundsException();
        }

        return indexToDelete;
    }

    /**
     * Returns the parameters used for add loan command as a String Array of size 3
     * <pre>
     * commandParameters[0]: Description
     * commandParameters[1]: Amount Loaned
     * commandParameters[2]: Loan Return Date & Time
     * </pre>
     *
     * @param userInput String input by user
     * @return The parameters used for add loan command
     * @throws AddLoanCommandWrongFormatException If any empty fields or wrong sub command or wrong sub command order
     *                                            or wrong format of amount field (alphabets instead of numbers)
     */
    public String[] parseAddLoanCommand(String userInput) throws AddLoanCommandWrongFormatException {
        final int numberOfAddLoanCommandParameters = 3;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddLoanCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/") ||
                !userInput.contains("r/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/")) ||
                (userInput.indexOf("r/") < userInput.indexOf("a/")) ||
                (userInput.indexOf("r/") < userInput.indexOf("d/"));

        if (hasInvalidSubcommand || hasInvalidSubcommandOrder) {
            throw new AddLoanCommandWrongFormatException();
        }

        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand,
                userInput.indexOf("r/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("r/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty() ||
                commandParameters[2].isEmpty();

        if (hasInvalidParameters) {
            throw new AddLoanCommandWrongFormatException();
        }

        return commandParameters;
    }

    /**
     * Returns the parameters used for add income command as a String Array of size 2
     * commandParameters[0]: Description
     * commandParameters[1]: Amount Earned
     *
     * @param userInput String input by user
     * @return The parameters used for add income command
     * @throws AddIncomeCommandWrongFormatException If any empty fields or wrong sub command or wrong sub command order
     */
    public String[] parseAddIncomeCommand(String userInput) throws AddIncomeCommandWrongFormatException {
        final int numberOfAddIncomeCommandParameters = 2;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddIncomeCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/"));

        if (hasInvalidSubcommand || hasInvalidSubcommandOrder) {
            throw new AddIncomeCommandWrongFormatException();
        }

        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty();

        if (hasInvalidParameters) {
            throw new AddIncomeCommandWrongFormatException();
        }

        return commandParameters;
    }

    /**
     * Returns the index to delete if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to delete
     * @throws DeleteIncomeCommandIndexOutOfBoundsException If index to delete does not exist
     */
    public int parseDeleteIncomeCommand(String userInput) throws DeleteIncomeCommandIndexOutOfBoundsException {
        final int sizeOfDeleteIncome = "delete income".length();

        if (userInput.substring(sizeOfDeleteIncome).trim().isEmpty()) {
            throw new DeleteIncomeCommandIndexOutOfBoundsException();
        }

        int indexToDelete;
        try {
            indexToDelete = Integer.parseInt(userInput.substring(sizeOfDeleteIncome).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new DeleteIncomeCommandIndexOutOfBoundsException();
        }

        if (indexToDelete < 0 || indexToDelete >= Income.numberOfIncomes) {
            throw new DeleteIncomeCommandIndexOutOfBoundsException();
        }

        return indexToDelete;
    }

    /**
     * Returns the parameters used for edit income command as a String Array of size 3
     * commandParameters[0]: Index To Edit
     * commandParameters[1]: Description
     * commandParameters[2]: Amount Earned
     *
     * @param userInput String input by user
     * @return The parameters used for edit income command
     * @throws EditIncomeCommandIndexOutOfBoundsException If index does not exist
     * @throws EditIncomeCommandWrongFormatException      If any empty fields or wrong sub command or
     *                                                    wrong sub command order
     */
    public String[] parseEditIncomeCommand(String userInput)
            throws EditIncomeCommandIndexOutOfBoundsException, EditIncomeCommandWrongFormatException {

        final int numberOfAddIncomeCommandParameters = 3;
        final int sizeOfSubcommand = 2;
        final int sizeOfEditIncome = "edit income".length();
        String[] commandParameters = new String[numberOfAddIncomeCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/"));

        if (hasInvalidSubcommand || hasInvalidSubcommandOrder) {
            throw new EditIncomeCommandWrongFormatException();
        }

        String indexToEdit = userInput.substring(sizeOfEditIncome, userInput.indexOf("d/")).trim();

        if (indexToEdit.isEmpty()) {
            throw new EditIncomeCommandIndexOutOfBoundsException();
        }

        if (Float.parseFloat(indexToEdit) <= 0 || Float.parseFloat(indexToEdit) > Income.numberOfIncomes) {
            throw new EditIncomeCommandIndexOutOfBoundsException();
        }

        commandParameters[0] = indexToEdit;
        commandParameters[1] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty() ||
                commandParameters[2].isEmpty();

        if (hasInvalidParameters) {
            throw new EditIncomeCommandWrongFormatException();
        }

        return commandParameters;
    }

    /**
     * Returns the index to mark if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to set repaid
     * @throws LoanRepaidCommandIndexOutOfBoundsException If index to set repaid does not exist or missing
     */
    public int parseLoanRepaidCommand(String userInput) throws LoanRepaidCommandIndexOutOfBoundsException {
        final int sizeOfLoanRepaid = "loan repaid".length();
        String indexToSetRepaidString = userInput.substring(sizeOfLoanRepaid).trim();
        if (indexToSetRepaidString.isEmpty()) {
            throw new LoanRepaidCommandIndexOutOfBoundsException();
        }

        int indexToSetRepaid = Integer.parseInt(indexToSetRepaidString) - 1;
        if (indexToSetRepaid < 0 || indexToSetRepaid >= Loan.numberOfLoans) {
            throw new LoanRepaidCommandIndexOutOfBoundsException();
        }

        return indexToSetRepaid;
    }

    /**
     * Returns the parameters required for the add investment command as a String Array of size 3
     * <pre>
     * commandParameters[0]: Description
     * commandParameters[1]: Amount Invested
     * commandParameters[2]: Monthly Deposit Date
     * </pre>
     *
     * @param userInput String input by user
     * @return The parameters used for add investment command
     * @throws AddInvestmentSubcommandException If the required parameters inserted by the user are missing or empty
     */
    private String[] parseAddInvestmentCommand(String userInput)
            throws AddInvestmentSubcommandException, AddInvestmentSubcommandOrderException {
        final int numberOfAddInvestmentCommandParameters = 3;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddInvestmentCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/")
                || !userInput.contains("m/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/")) ||
                (userInput.indexOf("m/") < userInput.indexOf("a/")) ||
                (userInput.indexOf("m/") < userInput.indexOf("d/"));
        if (hasInvalidSubcommand) {
            throw new AddInvestmentSubcommandException();
        }
        if (hasInvalidSubcommandOrder) {
            throw new AddInvestmentSubcommandOrderException();
        }

        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand,
                userInput.indexOf("m/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("m/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty() ||
                commandParameters[2].isEmpty();
        if (hasInvalidParameters) {
            throw new AddInvestmentSubcommandException();
        }

        return commandParameters;
    }

    /**
     * Returns the index of an investment object to delete as requested by the user.
     *
     * @param userInput String input by user
     * @return The index of an investment object to delete in the Investment ArrayList
     * @throws DeleteInvestmentIndexOutOfBoundsException  If the user requested index does not exist
     * @throws DeleteInvestmentMissingIndexException      If the user did not indicate an index to delete
     * @throws DeleteInvestmentWrongNumberFormatException If the user uses non-numeric value for the index
     */
    private int parseDeleteInvestmentCommand(String userInput) throws DeleteInvestmentIndexOutOfBoundsException,
            DeleteInvestmentMissingIndexException, DeleteInvestmentWrongNumberFormatException {
        final int sizeOfInvestmentCommand = "delete investment".length();
        String indexToDeleteString = userInput.substring(sizeOfInvestmentCommand).trim();
        if (indexToDeleteString.isEmpty()) {
            throw new DeleteInvestmentMissingIndexException();
        }
        int indexToDelete = 0;
        try {
            indexToDelete = Integer.parseInt(indexToDeleteString) - 1;
        } catch (NumberFormatException e) {
            throw new DeleteInvestmentWrongNumberFormatException();
        }
        if (indexToDelete < 0 || indexToDelete >= investmentList.getSize()) {
            throw new DeleteInvestmentIndexOutOfBoundsException();
        }

        return indexToDelete;
    }


}
