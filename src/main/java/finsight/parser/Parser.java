package finsight.parser;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;
import finsight.income.exceptions.DeleteIncomeCommandIndexOutOfBoundsException;
import finsight.income.exceptions.EditIncomeCommandIndexOutOfBoundsException;
import finsight.income.incomelist.IncomeList;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;

import finsight.loan.Loan;
import finsight.loan.loanlist.LoanList;
import finsight.ui.Ui;

/**
 * Takes in the user input and interpret which command to run
 *
 * @author Emannuel Tan Jing Yue
 * @author Lai Kai Jie Jeremy
 * @since 2025-10-08
 */
public class Parser {
    protected LoanList loanList;
    protected IncomeList incomeList;
    protected Ui ui;

    public Parser(LoanList loanList,IncomeList incomeList, Ui ui) {
        this.loanList = loanList;
        this.incomeList = incomeList;
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
        } catch (AddLoanCommandWrongFormatException | DeleteLoanCommandIndexOutOfBoundsException |
                 AddIncomeCommandWrongFormatException | DeleteIncomeCommandIndexOutOfBoundsException |
                 EditIncomeCommandIndexOutOfBoundsException e) {
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
            throws AddLoanCommandWrongFormatException, DeleteLoanCommandIndexOutOfBoundsException, AddIncomeCommandWrongFormatException, DeleteIncomeCommandIndexOutOfBoundsException, EditIncomeCommandIndexOutOfBoundsException {

        if (userInput.startsWith("list loan")) {
            loanList.listLoans();
        } else if (userInput.startsWith("add loan")) {
            String[] commandParameters = parseAddLoanCommand(userInput);
            loanList.addLoan(new Loan(commandParameters[0], commandParameters[1], commandParameters[2]));
        } else if (userInput.startsWith("delete loan")) {
            int indexToDelete = parseDeleteLoanCommand(userInput);
            loanList.deleteLoan(indexToDelete);
        }else if(userInput.startsWith("add income")) {
            String[] commandParameters = parseAddIncomeCommmand(userInput);
            incomeList.addIncome(new Income(commandParameters[0], commandParameters[1]));
        }else if(userInput.startsWith("delete income")) {
            int indexToDelete = parseDeleteIncomeCommand(userInput);
            incomeList.deleteIncome(indexToDelete);
        }else if(userInput.startsWith("edit income")) {
            String[] commandParameters = parseEditIncomeCommand(userInput);
            incomeList.editIncome(commandParameters[0],commandParameters[1],commandParameters[2]);
        }
        else {
            ui.printPossibleCommands();
        }
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

    public String[] parseAddIncomeCommmand(String userInput) throws AddIncomeCommandWrongFormatException {
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

    public int parseDeleteIncomeCommand(String userInput) throws DeleteIncomeCommandIndexOutOfBoundsException {
        final int sizeOfDeleteIncome = 13;
        int indexToDelete = Integer.parseInt(userInput.substring(sizeOfDeleteIncome).trim());

        if (indexToDelete <= 0 || indexToDelete > Income.numberOfIncomes) {
            throw new DeleteIncomeCommandIndexOutOfBoundsException();
        }

        return indexToDelete - 1;
    }

    public String[] parseEditIncomeCommand(String userInput) throws EditIncomeCommandIndexOutOfBoundsException {
        final int numberOfAddIncomeCommandParameters = 3;
        final int sizeOfSubcommand = 2;
        final int sizeOfEditIncome = 11;
        String[] commandParameters = new String[numberOfAddIncomeCommandParameters];

        String indexToEdit = userInput.substring(sizeOfEditIncome,userInput.indexOf("d/")).trim();

        if (Float.parseFloat(indexToEdit) <= 0 || Float.parseFloat(indexToEdit) > Income.numberOfIncomes){
            throw new EditIncomeCommandIndexOutOfBoundsException();
        }

        commandParameters[0] = indexToEdit;
        commandParameters[1] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand).trim();

        return commandParameters;
    }
}
