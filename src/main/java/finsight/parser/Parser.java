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

import finsight.loan.exceptions.AddLoanCommandInvalidAmountException;
import finsight.loan.exceptions.AddLoanCommandPastDateUsedException;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;
import finsight.loan.exceptions.DeleteLoanCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.EditLoanCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.EditLoanCommandInvalidAmountException;
import finsight.loan.exceptions.EditLoanCommandPastDateUsedException;
import finsight.loan.exceptions.EditLoanCommandWrongFormatException;
import finsight.loan.exceptions.LoanRepaidCommandIndexOutOfBoundsException;
import finsight.loan.exceptions.LoanNotRepaidCommandIndexOutOfBoundsException;
import finsight.loan.Loan;
import finsight.loan.loanlist.LoanList;

import finsight.ui.Ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public Parser(ExpenseList expenseList, IncomeList incomeList, InvestmentList investmentList, LoanList loanList) {
        this.expenseList = expenseList;
        this.incomeList = incomeList;
        this.investmentList = investmentList;
        this.loanList = loanList;
    }

    /**
     * Try to run a command and handle any exceptions thrown
     *
     * @param userInput String input by the user
     */
    public void tryCommand(String userInput) {
        try {
            handleCommand(userInput);
        } catch (AddExpenseCommandWrongFormatException | AddInvestmentDateOutOfBoundsException |
                 AddInvestmentSubcommandException | AddInvestmentSubcommandOrderException |
                 AddInvestmentWrongNumberFormatException | AddIncomeCommandWrongFormatException |
                 AddLoanCommandInvalidAmountException | AddLoanCommandPastDateUsedException |
                 AddLoanCommandWrongFormatException | DeleteExpenseCommandIndexOutOfBoundsException |
                 DeleteIncomeCommandIndexOutOfBoundsException | DeleteInvestmentIndexOutOfBoundsException |
                 DeleteInvestmentMissingIndexException | DeleteInvestmentWrongNumberFormatException |
                 DeleteLoanCommandIndexOutOfBoundsException | EditIncomeCommandWrongFormatException |
                 EditIncomeCommandIndexOutOfBoundsException | EditLoanCommandIndexOutOfBoundsException |
                 EditLoanCommandInvalidAmountException | EditLoanCommandPastDateUsedException |
                 EditLoanCommandWrongFormatException | LoanRepaidCommandIndexOutOfBoundsException |
                 LoanNotRepaidCommandIndexOutOfBoundsException | IOException e) {
            Ui.printErrorMessage(e.getMessage());
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
     *                                                       or incorrect sub command
     * @throws AddLoanCommandInvalidAmountException          If add loan command is used with negative or
     *                                                       zero amount loaned
     * @throws AddLoanCommandPastDateUsedException           If date input by user is in the past for add loan command
     * @throws AddLoanCommandWrongFormatException            If any empty fields or wrong sub command or
     *                                                       wrong sub command order or
     *                                                       wrong format of amount field (alphabets instead of numbers)
     *                                                       or wrong format of date field
     * @throws DeleteExpenseCommandIndexOutOfBoundsException If delete expense command used with out-of-bounds index
     * @throws DeleteIncomeCommandIndexOutOfBoundsException  If delete income command used with non-existing index or
     *                                                       index missing
     * @throws DeleteInvestmentIndexOutOfBoundsException     If delete investment command is used with non-existing
     *                                                       index
     * @throws DeleteInvestmentMissingIndexException         If delete investment command is used without inserting an
     *                                                       index
     * @throws DeleteLoanCommandIndexOutOfBoundsException    If delete loan command used with non-existing index or
     *                                                       index missing or has alphabets
     * @throws EditIncomeCommandWrongFormatException         If edit income command has empty fields, incorrect format
     *                                                       or incorrect sub commands
     * @throws EditIncomeCommandIndexOutOfBoundsException    If edit income command used with non-existing index or
     *                                                       index missing
     * @throws EditLoanCommandIndexOutOfBoundsException      If edit loan command used with non-existing index or
     *                                                       index missing or alphabets was used
     * @throws EditLoanCommandInvalidAmountException         If edit loan command is used with negative or
     *                                                       zero amount loaned
     * @throws EditLoanCommandPastDateUsedException          If date input by user is in the past for edit loan command
     * @throws EditLoanCommandWrongFormatException           If any empty fields or wrong sub command or
     *                                                       wrong sub command order or
     *                                                       wrong format of amount field (alphabets instead of numbers)
     *                                                       or wrong format of date field
     * @throws LoanRepaidCommandIndexOutOfBoundsException    If loan repaid command used with non-existing index or
     *                                                       index missing or alphabets was used
     * @throws LoanNotRepaidCommandIndexOutOfBoundsException If loan not repaid command used with non-existing index or
     *                                                       index missing or alphabets was used
     * @throws IOException                                   If an I/O errors occurs when reading from
     *                                                       or writing to file
     */
    public void handleCommand(String userInput)
            throws AddExpenseCommandWrongFormatException, AddInvestmentDateOutOfBoundsException,
            AddInvestmentSubcommandException, AddInvestmentSubcommandOrderException,
            AddInvestmentWrongNumberFormatException, AddIncomeCommandWrongFormatException,
            AddLoanCommandInvalidAmountException, AddLoanCommandPastDateUsedException,
            AddLoanCommandWrongFormatException, DeleteExpenseCommandIndexOutOfBoundsException,
            DeleteIncomeCommandIndexOutOfBoundsException, DeleteInvestmentIndexOutOfBoundsException,
            DeleteInvestmentMissingIndexException, DeleteInvestmentWrongNumberFormatException,
            DeleteLoanCommandIndexOutOfBoundsException, EditIncomeCommandWrongFormatException,
            EditIncomeCommandIndexOutOfBoundsException, EditLoanCommandIndexOutOfBoundsException,
            EditLoanCommandInvalidAmountException, EditLoanCommandPastDateUsedException,
            EditLoanCommandWrongFormatException, LoanRepaidCommandIndexOutOfBoundsException,
            LoanNotRepaidCommandIndexOutOfBoundsException, IOException {

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

        } else if (userInput.toLowerCase().startsWith("loan not repaid")) {
            int indexToSetNotRepaid = parseLoanNotRepaidCommand(userInput);
            assert (indexToSetNotRepaid >= 0 && indexToSetNotRepaid < Loan.numberOfLoans);
            loanList.setNotRepaid(indexToSetNotRepaid);

        } else if (userInput.toLowerCase().startsWith("edit loan")) {
            String[] commandParameters = parseEditLoanCommand(userInput);
            assert (!commandParameters[0].isEmpty() && !commandParameters[1].isEmpty()
                    && !commandParameters[2].isEmpty() && !commandParameters[3].isEmpty());
            loanList.editLoan(commandParameters);

        } else if (userInput.toLowerCase().startsWith("add income")) {
            String[] commandParameters = parseAddIncomeCommand(userInput);
            assert (!commandParameters[0].isEmpty() && !commandParameters[1].isEmpty());
            incomeList.addIncome(new Income(commandParameters[0], commandParameters[1]));

        } else if (userInput.toLowerCase().startsWith("delete income")) {
            int indexToDelete = parseDeleteIncomeCommand(userInput);
            assert (indexToDelete >= 0 && indexToDelete < Income.numberOfIncomes);
            incomeList.deleteIncome(indexToDelete);

        } else if (userInput.toLowerCase().startsWith("edit income")) {
            String[] commandParameters = parseEditIncomeCommand(userInput);
            assert (!commandParameters[0].isEmpty() && !commandParameters[1].isEmpty()
                    && !commandParameters[2].isEmpty());
            incomeList.editIncome(commandParameters[0], commandParameters[1], commandParameters[2]);

        } else if (userInput.toLowerCase().startsWith("list income overview")) {
            incomeList.listIncomeOverview();

        } else if (userInput.toLowerCase().startsWith("list income")) {
            incomeList.listIncomes();

        } else if (userInput.toLowerCase().startsWith("list expense")) {
            expenseList.listExpenses();

        } else if (userInput.toLowerCase().startsWith("add expense")) {
            String[] commandParameters = parseAddExpenseCommand(userInput);
            assert (!commandParameters[0].isEmpty() && !commandParameters[1].isEmpty()
                    && !commandParameters[2].isEmpty());
            expenseList.addExpense(new Expense(commandParameters[0], commandParameters[1]));

        } else if (userInput.toLowerCase().startsWith("delete expense")) {
            int indexToDelete = parseDeleteExpenseCommand(userInput);
            assert (indexToDelete >= 0 && indexToDelete < Expense.numberOfExpenses);
            expenseList.deleteExpense(indexToDelete);

        } else if (userInput.toLowerCase().startsWith("list investment")) {
            investmentList.listAllInvestments();

        } else if (userInput.toLowerCase().startsWith("add investment")) {
            String[] commandParameters = parseAddInvestmentCommand(userInput);
            investmentList.addInvestment(new Investment(commandParameters[0],
                    commandParameters[1], commandParameters[2], commandParameters[3]));

        } else if (userInput.toLowerCase().startsWith("delete investment")) {
            int indexToDelete = parseDeleteInvestmentCommand(userInput);
            assert indexToDelete >= 0 && indexToDelete < Investment.numberOfInvestments;
            investmentList.deleteInvestment(indexToDelete);

        } else if(userInput.toLowerCase().startsWith("help")) {
            Ui.printPossibleCommands();

        } else {
            Ui.printInvalidCommandMessage();
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
    public int parseDeleteExpenseCommand(String userInput) throws DeleteExpenseCommandIndexOutOfBoundsException {
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
    public String[] parseAddExpenseCommand(String userInput) throws
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


        boolean hasInvalidParameters = commandParameters[0].isEmpty() ||
                commandParameters[1].isEmpty();

        if (hasInvalidParameters) {
            throw new AddExpenseCommandWrongFormatException();
        }

        boolean isNegativeNumber= Double.parseDouble(commandParameters[1])<0;
        if (isNegativeNumber) {
            throw new AddExpenseCommandWrongFormatException();
        }

        return commandParameters;
    }

    //@@ author Emannuel-Tan

    /**
     * Checks if the loan description already exists in LoanList
     * If found, output a reminder
     * If not found, no additional output
     * @param description Description of the loan to add / to be edited into
     */
    public void checkIfLoanAlreadyInList(String description) {
        for (int i = 0; i < Loan.numberOfLoans; i++) {
            if (loanList.getLoans().get(i).getDescription().equals(description)) {
                Ui.printLoanAlreadyInListReminder(description);
                break;
            }
        }
    }

    /**
     * Returns the index to delete if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to delete
     * @throws DeleteLoanCommandIndexOutOfBoundsException If index to delete does not exist or missing or has alphabets
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
     * @throws AddLoanCommandInvalidAmountException If amount loaned is negative or zero
     * @throws AddLoanCommandPastDateUsedException  If date used is in the past
     * @throws AddLoanCommandWrongFormatException   If any empty fields or wrong sub command or
     *                                              wrong sub command order or
     *                                              wrong format of amount field (alphabets instead of numbers) or
     *                                              wrong format of date field
     */
    public String[] parseAddLoanCommand(String userInput)
            throws AddLoanCommandInvalidAmountException, AddLoanCommandPastDateUsedException,
            AddLoanCommandWrongFormatException {
        final int numberOfAddLoanCommandParameters = 3;
        final int sizeOfSubcommand = 2;
        DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
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

        checkIfLoanAlreadyInList(commandParameters[0]);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime loanReturnDate;
        double amountLoaned;
        try {
            amountLoaned = Double.parseDouble(commandParameters[1]);
            loanReturnDate = LocalDateTime.parse(commandParameters[2], inputDateFormat);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new AddLoanCommandWrongFormatException();
        }

        if (loanReturnDate.isBefore(currentTime)) {
            throw new AddLoanCommandPastDateUsedException();
        }
        if (amountLoaned < 0.01) {
            throw new AddLoanCommandInvalidAmountException();
        }

        return commandParameters;
    }

    /**
     * Returns the parameters used for edit loan command as a String Array of size 4
     * <pre>
     * commandParameters[0]: Index To Edit
     * commandParameters[1]: Description
     * commandParameters[2]: Amount Loaned
     * commandParameters[3]: Loan Return Date & Time
     * </pre>
     *
     * @param userInput String input by user
     * @return The parameters used for edit loan command
     * @throws EditLoanCommandIndexOutOfBoundsException If index to edit does not exist or missing or has alphabets
     * @throws EditLoanCommandInvalidAmountException    If amount loaned is negative or zero
     * @throws EditLoanCommandPastDateUsedException     If date input is in the past
     * @throws EditLoanCommandWrongFormatException      If any empty fields or wrong sub command or
     *                                                  wrong sub command order or
     *                                                  wrong format of amount field (alphabets instead of numbers) or
     *                                                  wrong format of date field
     */
    public String[] parseEditLoanCommand(String userInput)
            throws EditLoanCommandIndexOutOfBoundsException, EditLoanCommandInvalidAmountException,
            EditLoanCommandPastDateUsedException, EditLoanCommandWrongFormatException {
        final int sizeOfEditLoan = "edit loan".length();
        final int numberOfEditLoanCommandParameters = 4;
        final int sizeOfSubcommand = 2;
        DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String[] commandParameters = new String[numberOfEditLoanCommandParameters];

        boolean hasInvalidSubcommand = !userInput.contains("d/") || !userInput.contains("a/") ||
                !userInput.contains("r/");
        boolean hasInvalidSubcommandOrder = (userInput.indexOf("a/") < userInput.indexOf("d/")) ||
                (userInput.indexOf("r/") < userInput.indexOf("a/")) ||
                (userInput.indexOf("r/") < userInput.indexOf("d/"));

        if (hasInvalidSubcommand || hasInvalidSubcommandOrder) {
            throw new EditLoanCommandWrongFormatException();
        }

        String indexToEditString = userInput.substring(sizeOfEditLoan, userInput.indexOf("d/")).trim();
        if (indexToEditString.isEmpty()) {
            throw new EditLoanCommandIndexOutOfBoundsException();
        }

        int indexToEdit;
        try {
            indexToEdit = Integer.parseInt(indexToEditString) - 1;
        } catch (NumberFormatException e) {
            throw new EditLoanCommandIndexOutOfBoundsException();
        }

        if (indexToEdit < 0 || indexToEdit >= Loan.numberOfLoans) {
            throw new EditLoanCommandIndexOutOfBoundsException();
        }

        commandParameters[0] = indexToEditString;
        commandParameters[1] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand,
                userInput.indexOf("r/")).trim();
        commandParameters[3] = userInput.substring(userInput.indexOf("r/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[1].isEmpty() || commandParameters[2].isEmpty() ||
                commandParameters[3].isEmpty();

        if (hasInvalidParameters) {
            throw new EditLoanCommandWrongFormatException();
        }

        checkIfLoanAlreadyInList(commandParameters[1]);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime loanReturnDate;
        double amountLoaned;
        try {
            amountLoaned = Double.parseDouble(commandParameters[2]);
            loanReturnDate = LocalDateTime.parse(commandParameters[3], inputDateFormat);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new EditLoanCommandWrongFormatException();
        }

        if (loanReturnDate.isBefore(currentTime)) {
            throw new EditLoanCommandPastDateUsedException();
        }
        if (amountLoaned < 0.01) {
            throw new EditLoanCommandInvalidAmountException();
        }

        return commandParameters;
    }
    //@@ author

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


        boolean hasInvalidParameters = commandParameters[0].isEmpty() ||
                commandParameters[1].isEmpty();

        if (hasInvalidParameters) {
            throw new AddIncomeCommandWrongFormatException();
        }

        boolean isNegativeNumber= Double.parseDouble(commandParameters[1])<0;
        if (isNegativeNumber) {
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

    //@@ author Emannuel-Tan

    /**
     * Returns the index to set repaid if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to set repaid
     * @throws LoanRepaidCommandIndexOutOfBoundsException If index to set repaid does not exist or
     *                                                    missing or has alphabets
     */
    public int parseLoanRepaidCommand(String userInput) throws LoanRepaidCommandIndexOutOfBoundsException {
        final int sizeOfLoanRepaid = "loan repaid".length();
        String indexToSetRepaidString = userInput.substring(sizeOfLoanRepaid).trim();
        int indexToSetRepaid;

        if (indexToSetRepaidString.isEmpty()) {
            throw new LoanRepaidCommandIndexOutOfBoundsException();
        }

        try {
            indexToSetRepaid = Integer.parseInt(indexToSetRepaidString) - 1;
        } catch (NumberFormatException e) {
            throw new LoanRepaidCommandIndexOutOfBoundsException();
        }


        if (indexToSetRepaid < 0 || indexToSetRepaid >= Loan.numberOfLoans) {
            throw new LoanRepaidCommandIndexOutOfBoundsException();
        }

        return indexToSetRepaid;
    }

    /**
     * Returns the index to set not repaid if index exists,
     * else throws exception
     *
     * @param userInput String input by user
     * @return The index to set repaid
     * @throws LoanNotRepaidCommandIndexOutOfBoundsException If index to set repaid does not exist or
     *                                                       missing or has alphabets
     */
    public int parseLoanNotRepaidCommand(String userInput) throws LoanNotRepaidCommandIndexOutOfBoundsException {
        final int sizeOfLoanNotRepaid = "loan not repaid".length();
        String indexToSetNotRepaidString = userInput.substring(sizeOfLoanNotRepaid).trim();
        int indexToSetNotRepaid;

        if (indexToSetNotRepaidString.isEmpty()) {
            throw new LoanNotRepaidCommandIndexOutOfBoundsException();
        }

        try {
            indexToSetNotRepaid = Integer.parseInt(indexToSetNotRepaidString) - 1;
        } catch (NumberFormatException e) {
            throw new LoanNotRepaidCommandIndexOutOfBoundsException();
        }


        if (indexToSetNotRepaid < 0 || indexToSetNotRepaid >= Loan.numberOfLoans) {
            throw new LoanNotRepaidCommandIndexOutOfBoundsException();
        }

        return indexToSetNotRepaid;
    }
    //@@ author

    /**
     * Returns the parameters required for the add investment command as a String Array of size 3
     * commandParameters[0]: Description
     * commandParameters[1]: Amount Invested
     * commandParameters[2]: Annual Return Rate
     * commandParameters[3]: Monthly Deposit Date
     *
     * @param userInput String input by user
     * @return The parameters used for add investment command
     * @throws AddInvestmentSubcommandException If the required parameters inserted by the user are missing or empty
     */
    public String[] parseAddInvestmentCommand(String userInput)
            throws AddInvestmentSubcommandException, AddInvestmentSubcommandOrderException {
        final int numberOfAddInvestmentCommandParameters = 4;
        final int sizeOfSubcommand = 2;
        String[] commandParameters = new String[numberOfAddInvestmentCommandParameters];

        addInvestmentInputValidation(userInput);
        // 0 - description, 1 - amount, 2 - return rate, 3 - date of month
        commandParameters[0] = userInput.substring(userInput.indexOf("d/") + sizeOfSubcommand,
                userInput.indexOf("a/")).trim();
        commandParameters[1] = userInput.substring(userInput.indexOf("a/") + sizeOfSubcommand,
                userInput.indexOf("r/")).trim();
        commandParameters[2] = userInput.substring(userInput.indexOf("r/") + sizeOfSubcommand,
                userInput.indexOf("m/")).trim();
        commandParameters[3] = userInput.substring(userInput.indexOf("m/") + sizeOfSubcommand).trim();

        boolean hasInvalidParameters = commandParameters[0].isEmpty() || commandParameters[1].isEmpty() ||
                commandParameters[2].isEmpty() || commandParameters[3].isEmpty();
        if (hasInvalidParameters) {
            throw new AddInvestmentSubcommandException();
        }

        boolean isNegativeNumber= Double.parseDouble(commandParameters[1])<0;
        if (isNegativeNumber) {
            throw new AddInvestmentSubcommandException();
        }

        return commandParameters;
    }

    /**
     * Validates the user input and throws appropriate exceptions if invalid.
     * Helper function to parseAddInvestmentCommand(...)
     *
     * @param userInput String input by user
     * @throws AddInvestmentSubcommandException      If the required parameters inserted by
     *                                               the user are missing or empty
     * @throws AddInvestmentSubcommandOrderException If the required parameters inserted by
     *                                               the user are in the wrong order
     */
    private void addInvestmentInputValidation(String userInput)
            throws AddInvestmentSubcommandException, AddInvestmentSubcommandOrderException {
        boolean hasInvalidSubcommand = !userInput.contains("d/") ||
                !userInput.contains("a/") ||
                !userInput.contains("r/") ||
                !userInput.contains("m/");
        boolean hasValidSubcommandOrder = (userInput.indexOf("d/") < userInput.indexOf("a/") &&
                (userInput.indexOf("r/") < userInput.indexOf("m/")) &&
                (userInput.indexOf("a/") < userInput.indexOf("r/")));
        if (hasInvalidSubcommand) {
            throw new AddInvestmentSubcommandException();
        }
        if (!hasValidSubcommandOrder) {
            throw new AddInvestmentSubcommandOrderException();
        }
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
    public int parseDeleteInvestmentCommand(String userInput) throws DeleteInvestmentIndexOutOfBoundsException,
            DeleteInvestmentMissingIndexException, DeleteInvestmentWrongNumberFormatException {
        final int sizeOfInvestmentCommand = "delete investment".length();
        String indexToDeleteString = userInput.substring(sizeOfInvestmentCommand).trim();

        if (indexToDeleteString.isEmpty()) {
            throw new DeleteInvestmentMissingIndexException();
        }
        int indexToDelete;
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
