# User Guide

## Introduction

FinSight is a CLI-based app for managing finances such as income, expenses, loans and investments.

* **Loans**
  * [List loan](#list-all-loans-list-loan)
  * [Add loan](#add-a-loan-add-loan)
  * [Delete loan](#delete-a-loan-delete-loan)
  * [Loan repaid](#set-loan-as-repaid-loan-repaid)
  * [Loan not repaid](#set-loan-as-not-repaid-loan-not-repaid)
  * [Edit loan](#edit-a-loan-edit-loan)
* **Expense**
  * [List expense](#list-all-expenses-list-expense)
  * [add expense](#add-expense-add-expense)
  * [delete expense](#delete-expense-delete-expense)
* **Income**
  * [list income](#list-all-income-list-income)
  * [add income](#add-income-add-income)
  * [delete income](#delete-income-delete-income)
  * [edit income](#edit-income-edit-income)
  * [list income overview](#list-income-overview-list-income-overview)
* **Investment**
  * [list investment](#list-all-investment-list-investment)
  * [add investment](#add-investment-add-investment)
  * [delete investment](#delete-investment-delete-investment)

*  [Exiting the program](#exit-the-program-bye)

*  [Saving the data](#saving-the-data)

*  [FAQ](#faq)

*  [Command Summary](#command-summary)

---

<br/>

## Quick Start

1. Ensure that you have Java `17` or above installed.
2. Download the latest version of `FinSight` from [here](https://github.com/AY2526S1-CS2113-T10-1/tp).
3. Copy the file to the folder you want to use as the home folder for FinSight.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar finsight.jar` command to run the application.
5. Type the command into the terminal and press Enter to execute it. Any input given that is not a correct command will show all possible commands.
6. Refer to the [Features](#Features) below for details of each command.

---

<br/>

## Features

> [!NOTE]
> * Words in `<UPPER_CASE>` are the parameters to be supplied by the user.<br>
  e.g. in `delete loan <INDEX>`, `<INDEX>` is a parameter which can be used as `delete loan 1`.
> 
> * Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
> 
> * Parameters must be in exact order.<br>
  e.g. if the command specifies `d/<DESCRIPTION> a/<AMOUNT>`, the exact order must be followed for the command to work.
> 
> * Extraneous parameters for commands that do not take in parameters (such as `list loan`, `list expense`, `list investment`, `list income` and `bye`) will be ignored.<br>
  e.g. if the command specifies `bye 123`, it will be interpreted as `bye`.
> 
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

<br/>

### List all loans: `list loan`
Prints the list of all loans onto the terminal with an index starting from 1.

Format: `list loan`

### Add a loan: `add loan`
Adds a loan to the list. The loan will include a description and the amount borrowed as well as the date and time to return the loaned amount by.

Format: `add loan d/<DESCRIPTION> a/<AMOUNT_BORROWED> r/<LOAN_RETURN_DATE_AND_TIME>`

* The `<AMOUNT_BORROWED>` cannot contain punctuation.
* The `<LOAN_RETURN_DATE_AND_TIME>` must be of format (dd-MM-yyyy HH:mm).

Example of usage:

`add loan d/ loan 1 a/ 10000 r/ 10-10-2026 19:00`

`add loan d/ loan 2 a/ 10.56 r/ 11-10-2056 23:59`

### Delete a loan: `delete loan`
Deletes the loan at `<INDEX>` from the list of loan

Format: `delete loan <INDEX>`

* The `<INDEX>` cannot contain punctuation.

Example of usage:

`delete loan 1`

`delete loan 3`

### Set loan as repaid: `loan repaid`
Sets the loan at <INDEX> as repaid

Format: `loan repaid <INDEX>>`

* The `<INDEX>` cannot contain punctuation.

Example of usage:

`loan repaid 1`

`loan repaid 3`

### Set loan as not repaid: `loan not repaid`
Sets the loan at <INDEX> as not repaid

Format: `loan not repaid <INDEX>>`

* The `<INDEX>` cannot contain punctuation.

Example of usage:

`loan not repaid 1`

`loan not repaid 3`

### Edit a loan: `edit loan`
Edits a loan in the list. The loan will include a description and the amount borrowed as well as the date and time to return the loaned amount by.

Format: `edit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_BORROWED> r/<LOAN_RETURN_DATE_AND_TIME>`

* The `<INDEX>` cannot contain punctuation.
* The `<AMOUNT_BORROWED>` cannot contain punctuation.
* The `<LOAN_RETURN_DATE_AND_TIME>` must be of format (dd-MM-yyyy HH:mm).

Example of usage:

`edit loan 1 d/ loan 1 a/ 10000 r/ 10-10-2026 19:00`

`edit loan 2 d/ loan 2 a/ 10.56 r/ 11-10-2056 23:59`

<br/>

### List all expenses: `list expense`
Prints the list of all expenses onto the terminal, with starting index of 1

Format: `list expense`

### Add expense: `add expense`
Adds an expense to the list. The expense will include a description and the amount spent.

Format: `add expense d/<DECRIPTION> a/<AMOUNT_SPENT>`

Example of usage: `add expense d/food a/5.50`

### Delete expense: `delete expense`
Deletes the expense from the list.

Format: `delete expense <INDEX>`

* The `<INDEX>` cannot contain punctuation.

Example of usage: `delete expense 2`

<br/>

### List all income: `list income`
Prints the list of all expenses onto the terminal, with starting index of 1

Format: `list income`

### Add income: `add income`
Adds an income source to the list. The income includes description and the amount earned

Format: `add income d/<DESCRIPTION> a/<AMOUNT_EARNED>`

Example of usage:

`add income d/ job1 a/ 1000`

`add income d/ sidejob2 a/300`

### Delete income: `delete income`
Deletes the income at `<INDEX>` from the list of incomes

Format: `delete income <INDEX>`

Example of usage:

`delete income 2`

### Edit income: `edit income`
Edits the income at `<INDEX>` from the list. Requires description and amount earned.

Format: `edit income <INDEX> d/<DESCRIPTION> a/<AMOUNT_EARNED>`

Example of usage:

`edit income 1 d/newjob a/1200`

### List income overview `list income overview`
Prints the total income earned and the total expenses used. Also shows the remaining income.

Format: `list income overview`

<br/>

### List all investment: `list investment`
Prints the list of all investment onto the terminal, with starting index of 1. 
Also prints the total returns after 5 and 10 years.

Format: `list expense`

### Add investment: `add investment`
Adds an investment to the list. The investment will include a description, the monthly invested amount, 
the return rate, and the deposit date.

Format:`add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> r/<RETURN_RATE_PER_ANNUM> m/<DEPOSIT_DATE_EACH_MONTH>`

Example of usage: `add investment d/moomoo a/1000 r/3 m/21`

### Delete investment: `delete investment`
Delete the investment from the list.

Format: `delete investment <INDEX>`

Example of usage: `delete investment 3`

<br/>

### Exit the program: `bye`
Saves all current data and exits the program

Format: `bye`

### Saving the data
FinSight data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

---

<br/>

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Install the app in the other computer and overwrite the empty data files it creates with the files that contains the data of your previous FinSight home folder.

---

<br/>

## Command Summary

| Action                 | Format, Examples                                                                                                                                                       |
|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| List Loans             | `list loan`                                                                                                                                                            |
| Add a Loan             | `add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>`<br/> e.g. `add loan d/ loan1 a/10.55 r/ 10-10-2056 23:59`                                   |
| Delete a Loan          | `delete loan <INDEX>`<br/> e.g. `delete loan 1`                                                                                                                        |
| Set Loan as Repaid     | `loan repaid <INDEX>`<br/> e.g. `loan repaid 1`                                                                                                                        |
| Set Loan as Not Repaid | `loan not repaid <INDEX>`<br/> e.g. `loan not repaid 1`                                                                                                                |
| Edit a Loan            | `edit loan <INDEX> d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>`<br/> e.g. `edit loan 1 d/ loan1 a/10.55 r/ 10-10-2056 23:59`                       |
| List Expenses          | `list expense`                                                                                                                                                         |
| Add an Expense         | `add expense d/<DESCRIPTION> a/<AMOUNT_SPEND>`<br/> e.g. `add expense d/food a/6`                                                                                      |
| Delete An Expense      | `delete expense <INDEX>`<br/> e.g. `delete expense 2`                                                                                                                  |
| List Incomes           | `list income`                                                                                                                                                          |
| Add an Income          | `add income d/<DESCRIPTION> a/<AMOUNT_EARNED>`<br/> e.g. `add income d/ job1 a/ 1000`                                                                                  |
| Delete an Income       | `delete income <INDEX>`<br/> e.g. `delete income 3`                                                                                                                    |
| Edit an Income         | `edit income <INDEX> d/<DESCRIPTION> a/<AMOUNT_EARNED>`<br/> e.g. `edit income 1 d/newjob a/1200`                                                                      |
| List income overview   | `list income overview`                                                                                                                                                 |
| List Investments       | `list investment`                                                                                                                                                      |
| Add an Investment      | `add investment d/<DESCRIPTION> a/<AMOUNT_INVESTED_MONTHLY> r/<RETURN_RATE_PER_ANNUM> m/<DEPOSIT_DATE_EACH_MONTH>`<br/> e.g. `add investment d/moomoo a/1000 r/3 m/21` |
| Delete an Investment   | `delete investment <INDEX>`<br/> e.g. `delete investment 3`                                                                                                            |
| Exit Program           | `bye`                                                                                                                                                                  |
