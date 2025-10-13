package finsight.parser;

import finsight.expense.Expense;
import finsight.expense.expenselist.ExpenseList;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;

import finsight.loan.Loan;
import finsight.loan.loanlist.LoanList;
import finsight.ui.Ui;

/**
 * Takes in the user input and interpret which command to run
 *
 * @author Emannuel Tan Jing Yue
 * @since 2025-10-08
 */
public class Parser {
    protected LoanList loanList;
    protected ExpenseList expenseList;
    protected Ui ui;

    public Parser(LoanList loanList, Ui ui, ExpenseList expenseList) {
        this.loanList = loanList;
        this.expenseList = expenseList;
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
        } catch (AddLoanCommandWrongFormatException | DeleteLoanCommandIndexOutOfBoundsException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    /**
     * Take in user input and run the respective command and
     * throw exceptions as necessary
     *
     * @param userInput String input by the user
     * @throws AddLoanCommandWrongFormatException         If add loan command has empty fields or missing sub commands
     *                                                    or sub commands in wrong order
     * @throws DeleteLoanCommandIndexOutOfBoundsException If delete loan command used with non-existing index
     */
    public void handleCommand(String userInput)
            throws AddLoanCommandWrongFormatException, DeleteLoanCommandIndexOutOfBoundsException {

        if (userInput.toLowerCase().startsWith("list loan")) {
            loanList.listLoans();
        } else if (userInput.toLowerCase().startsWith("add loan")) {
            String[] commandParameters = parseAddLoanCommand(userInput);
            loanList.addLoan(new Loan(commandParameters[0], commandParameters[1], commandParameters[2]));
        } else if (userInput.toLowerCase().startsWith("delete loan")) {
            int indexToDelete = parseDeleteLoanCommand(userInput);
            loanList.deleteLoan(indexToDelete);
        } else if (userInput.toLowerCase().startsWith("list expense")) {
            expenseList.listExpenses();
        } else if (userInput.toLowerCase().startsWith("add expense")) {
            String[] commandParameters = parseAddExpenseCommand(userInput);
            Expense expenseToAdd = new Expense(commandParameters[0], commandParameters[1]);
            expenseList.addExpense(expenseToAdd);
        } else if (userInput.toLowerCase().startsWith("delete expense")) {
            int indexToDelete = parseDeleteExpenseCommand(userInput);
            expenseList.deleteExpense(indexToDelete);
        } else {
            ui.printPossibleCommands();
        }
    }

    private int parseDeleteExpenseCommand(String userInput) {
        final int sizeOfDeleteExpense = "delete expense".length();
        int indexToDelete = Integer.parseInt(userInput.substring(sizeOfDeleteExpense).trim())-1;

        return indexToDelete;
    }

    private String[] parseAddExpenseCommand(String userInput) {
        //throws if 'amount' contains alphabets -- "NumberFormatException"
        final int numberOfAddLoanCommandParameters = 2;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddLoanCommandParameters];

        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand).trim();

        return commandParameters;
    }

    /**
     * Returns the index to delete if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to delete
     * @throws DeleteLoanCommandIndexOutOfBoundsException If index to delete does not exist
     */
    public int parseDeleteLoanCommand(String userInput) throws DeleteLoanCommandIndexOutOfBoundsException {
        final int sizeOfDeleteLoan = 11;
        int indexToDelete = Integer.parseInt(userInput.substring(sizeOfDeleteLoan).trim());

        if (indexToDelete <= 0 || indexToDelete > Loan.numberOfLoans - 1) {
            throw new DeleteLoanCommandIndexOutOfBoundsException();
        }

        return indexToDelete;
    }

    /**
     * Returns the parameters used for add loan command as a String Array of size 3
     * commandParameters[0]: Description
     * commandParameters[1]: Amount Loaned
     * commandParameters[2]: Loan Return Date & Time
     *
     * @param userInput String input by user
     * @return The parameters used for add loan command
     * @throws AddLoanCommandWrongFormatException If any empty fields or wrong sub command or wrong sub command order
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
}
